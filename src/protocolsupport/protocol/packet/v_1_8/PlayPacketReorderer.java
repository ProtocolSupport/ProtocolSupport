package protocolsupport.protocol.packet.v_1_8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.server.v1_9_R1.EnumProtocol;
import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;

public class PlayPacketReorderer {

	protected Packet<?> animatePacket;

	public List<Packet<?>> orderPackets(Collection<? extends Packet<?>> packets) {
		ArrayList<Packet<?>> ordered = new ArrayList<>();
		for (Packet<?> curPacket : packets) {
			int packetId = ServerBoundPacket.getId(EnumProtocol.PLAY, curPacket);
			//if the packet is use entity, we attempt to add a cached animate packet after it
			if (packetId == ServerBoundPacket.PLAY_USE_ENTITY.getId()) {
				if (animatePacket != null) {
					ordered.add(curPacket);
					ordered.add(animatePacket);
					animatePacket = null;
				} else {
					ordered.add(curPacket);
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
				if (packetId == ServerBoundPacket.PLAY_ANIMATION.getId()) {
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
