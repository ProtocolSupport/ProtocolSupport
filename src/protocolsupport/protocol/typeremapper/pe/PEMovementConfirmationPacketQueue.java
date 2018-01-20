package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

//TODO: put a sane limit on queue size
public class PEMovementConfirmationPacketQueue {

	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(256);
	protected boolean foundLoginPlayStatus = false;
	protected boolean foundRespawnPlayStatus = false;
	protected boolean waitingConfirmation = false;

	@SuppressWarnings("unchecked")
	public RecyclableCollection<ClientBoundPacketData> attemptSendPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (!waitingConfirmation && !queue.isEmpty()) {
				ArrayList<ClientBoundPacketData> qclone = (ArrayList<ClientBoundPacketData>) queue.clone();
				queue.clear();
				processSendPackets(qclone, allowed);
			}
			processSendPackets(packets, allowed);
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	private void processSendPackets(Collection<ClientBoundPacketData> sendpackets, RecyclableArrayList<ClientBoundPacketData> allowed) {
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

	public boolean checkMovement(RecyclableCollection<ServerBoundPacketData> packets) {
		for (ServerBoundPacketData packet : packets) {
			if (waitingConfirmation && (packet.getPacketType() == ServerBoundPacket.PLAY_POSITION_LOOK)) {
				foundRespawnPlayStatus = false;
				waitingConfirmation = false;
				return true;
			}
		}
		return false;
	}

}
