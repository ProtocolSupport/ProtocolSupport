package protocolsupport.protocol.typeremapper.packet;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.utils.recyclable.Recyclable;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class AnimatePacketReorderer {

	protected IPacketData animatePacket;

	public RecyclableCollection<? extends IPacketData> orderPackets(RecyclableCollection<? extends IPacketData> packets) {
		try {
			RecyclableArrayList<IPacketData> ordered = RecyclableArrayList.create();
			for (IPacketData curPacket : packets) {
				PacketType packetType = curPacket.getPacketType();
				//if the packet is use entity, we attempt to add a cached animate packet after it
				if (packetType == PacketType.SERVERBOUND_PLAY_USE_ENTITY) {
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
					if (packetType == PacketType.SERVERBOUND_PLAY_ANIMATION) {
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

	public void release() {
		if (animatePacket != null) {
			Recyclable.recycle(animatePacket);
		}
	}

}
