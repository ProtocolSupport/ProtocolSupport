package protocolsupport.protocol.typeremapper.legacy;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class LegacyAnimatePacketReorderer {

	protected ServerBoundPacketData animatePacket;

	public RecyclableCollection<ServerBoundPacketData> orderPackets(RecyclableCollection<ServerBoundPacketData> packets) {
		try {
			RecyclableArrayList<ServerBoundPacketData> ordered = RecyclableArrayList.create();
			for (ServerBoundPacketData curPacket : packets) {
				ServerBoundPacket packetType = curPacket.getPacketType();
				//if the packet is use entity, we attempt to add a cached animate packet after it
				if (packetType == ServerBoundPacket.PLAY_USE_ENTITY) {
					ordered.add(curPacket);
					if (animatePacket != null) {
						ordered.add(animatePacket);
						animatePacket = null;
					}
				}
				//handle other packet types
				else {
					//attempt to add cached animate packet
					if (animatePacket != null) {
						ordered.add(animatePacket);
						animatePacket = null;
					}
					//if it is animate packet, we cache it
					if (packetType == ServerBoundPacket.PLAY_ANIMATION) {
						animatePacket = curPacket;
					}
					//any other type just gets added to ordered list
					else {
						ordered.add(curPacket);
					}
				}
			}
			return ordered;
		} finally {
			packets.recycleObjectOnly();
		}
	}

}
