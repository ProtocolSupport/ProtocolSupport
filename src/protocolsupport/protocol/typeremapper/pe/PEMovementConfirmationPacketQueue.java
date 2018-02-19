package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

//TODO: put a sane limit on queue size and replace booleans with enum state
public class PEMovementConfirmationPacketQueue {

	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(256);
	protected boolean foundLoginPlayStatus = false;
	protected boolean foundRespawnPlayStatus = false;
	protected boolean waitingConfirmation = false;
	protected boolean waitingUnlock = false;
	protected boolean unlockScheduled = false;

	@SuppressWarnings("unchecked")
	public RecyclableCollection<ClientBoundPacketData> processClientBoundPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (!waitingConfirmation && !queue.isEmpty()) {
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
			if (waitingConfirmation) {
				queue.add(sendpacket);
			} else {
				allowed.add(sendpacket);
				switch (sendpacket.getPacketId()) {
					case PEPacketIDs.PLAY_STATUS: {
						if (foundLoginPlayStatus) {
							foundRespawnPlayStatus = true;
						} else {
							foundLoginPlayStatus = true;
						}
						break;
					}
					case PEPacketIDs.PLAYER_MOVE: {
						if (foundRespawnPlayStatus) {
							waitingConfirmation = true;
						}
						break;
					}
				}
			}
		}
	}

	public void unlock() {
		foundRespawnPlayStatus = false;
		waitingConfirmation = false;
		waitingUnlock = false;
		unlockScheduled = false;
	}

	public RecyclableCollection<ServerBoundPacketData> processServerBoundPackets(RecyclableCollection<ServerBoundPacketData> packets) {
		try {
			RecyclableArrayList<ServerBoundPacketData> allowed = RecyclableArrayList.create();
			for (ServerBoundPacketData packet : packets) {
				if (waitingUnlock) {
					packet.recycle();
					continue;
				}
				if (waitingConfirmation && (packet.getPacketType() == ServerBoundPacket.PLAY_POSITION_LOOK)) {
					waitingUnlock = true;
				}
				allowed.add(packet);
			}
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	public boolean shouldScheduleUnlock() {
		if (!waitingUnlock) {
			return false;
		}
		if (unlockScheduled) {
			return false;
		}
		unlockScheduled = true;
		return true;
	}

}
