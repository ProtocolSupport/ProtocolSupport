package protocolsupport.protocol.legacyremapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class LegacyAnimatePacketReorderer {

	protected PacketCreator animatePacket;

	private final ArrayList<PacketCreator> ordered = new ArrayList<>();

	public List<PacketCreator> orderPackets(RecyclableCollection<PacketCreator> packetsR) {
		try {
			return orderPackets((Collection<PacketCreator>) packetsR);
		} finally {
			packetsR.recycle();
		}
	}

	public List<PacketCreator> orderPackets(Collection<PacketCreator> packets) {
		ordered.clear();
		for (PacketCreator curPacket : packets) {
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
	}

}
