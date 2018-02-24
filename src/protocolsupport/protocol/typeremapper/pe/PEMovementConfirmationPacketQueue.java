package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PEMovementConfirmationPacketQueue {

	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(2000);
	protected State state = State.SCANNING_FOR_LOGIN_STATUS;

	@SuppressWarnings("unchecked")
	public RecyclableCollection<ClientBoundPacketData> processClientBoundPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (isPacketSendingAllowed() && !queue.isEmpty()) {
				ArrayList<ClientBoundPacketData> qclone = (ArrayList<ClientBoundPacketData>) queue.clone();
				queue.clear();
				processClientBoundPackets0(qclone, allowed);
			}
			processClientBoundPackets0(packets, allowed);
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	private void processClientBoundPackets0(Collection<ClientBoundPacketData> sendpackets, RecyclableArrayList<ClientBoundPacketData> allowed) {
		for (ClientBoundPacketData sendpacket : sendpackets) {
			if (!isPacketSendingAllowed()) {
				queue.add(sendpacket);
			} else {
				allowed.add(sendpacket);
				switch (sendpacket.getPacketId()) {
					case PEPacketIDs.PLAY_STATUS: {
						state = state == State.SCANNING_FOR_LOGIN_STATUS ? State.SCANNING_FOR_PLAY_STATUS : State.SCANNING_FOR_SET_POSITION;
						break;
					}
					case PEPacketIDs.PLAYER_MOVE: {
						if (state == State.SCANNING_FOR_SET_POSITION) {
							state = State.WAITING_CLIENT_MOVE_CONFIRM;
						}
						break;
					}
				}
			}
		}
	}

	private boolean isPacketSendingAllowed() {
		switch (state) {
			case SCANNING_FOR_LOGIN_STATUS:
			case SCANNING_FOR_PLAY_STATUS:
			case SCANNING_FOR_SET_POSITION: {
				return true;
			}
			default: {
				return false;
			}
		}
	}

	public void unlock() {
		state = State.SCANNING_FOR_PLAY_STATUS;
	}

	public RecyclableCollection<ServerBoundPacketData> processServerBoundPackets(RecyclableCollection<ServerBoundPacketData> packets) {
		try {
			RecyclableArrayList<ServerBoundPacketData> allowed = RecyclableArrayList.create();
			for (ServerBoundPacketData packet : packets) {
				if ((state == State.WAITING_CLIENT_MOVE_CONFIRM) && (packet.getPacketType() == ServerBoundPacket.PLAY_POSITION_LOOK)) {
					state = State.WAITING_UNLOCK;
				}
				if ((state == State.WAITING_UNLOCK) || (state == State.UNLOCK_SCHEDULED)) {
					packet.recycle();
					continue;
				}
				allowed.add(packet);
			}
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	public boolean shouldScheduleUnlock() {
		if ((state != State.WAITING_UNLOCK) || (state == State.UNLOCK_SCHEDULED)) {
			return false;
		}
		state = State.UNLOCK_SCHEDULED;
		return true;
	}

	protected static enum State {
		SCANNING_FOR_LOGIN_STATUS, SCANNING_FOR_PLAY_STATUS, SCANNING_FOR_SET_POSITION, WAITING_CLIENT_MOVE_CONFIRM, WAITING_UNLOCK, UNLOCK_SCHEDULED
	}

}
