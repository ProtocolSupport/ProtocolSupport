package protocolsupport.protocol.typeremapper.packet;

import java.util.ArrayList;
import java.util.Collection;

import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PEDimensionSwitchConfirmationPacketQueue {

	protected final ArrayList<ClientBoundPacketData> queue = new ArrayList<>(128);
	protected boolean isLocked = false;

	//TODO: remove this, when pre/post middle packet send actions will be added
	public RecyclableCollection<ClientBoundPacketData> processClientBoundPackets(RecyclableCollection<ClientBoundPacketData> packets) {
		try {
			RecyclableArrayList<ClientBoundPacketData> allowed = RecyclableArrayList.create();
			if (isPacketSendingAllowed() && !queue.isEmpty()) {
				ArrayList<ClientBoundPacketData> qclone = new ArrayList<>(queue);
				queue.clear();
				processClientBoundPackets0(qclone, allowed);
			}
			processClientBoundPackets0(packets, allowed);
			return allowed;
		} finally {
			packets.recycleObjectOnly();
		}
	}

	protected boolean sentLoginPlayStatus = false;

	private void processClientBoundPackets0(Collection<ClientBoundPacketData> sendpackets, RecyclableArrayList<ClientBoundPacketData> allowed) {
		for (ClientBoundPacketData sendpacket : sendpackets) {
			if (!isPacketSendingAllowed()) {
				queue.add(sendpacket);
			} else {
				if (sendpacket.getPacketId() == PEPacketIDs.PLAY_STATUS) {
					if (sentLoginPlayStatus) {
						isLocked = true;
					} else {
						sentLoginPlayStatus = true;
					}
				}
				allowed.add(sendpacket);
			}
		}
	}

	private boolean isPacketSendingAllowed() {
		return !isLocked;
	}

	public void unlock() {
		isLocked = false;
	}

}
