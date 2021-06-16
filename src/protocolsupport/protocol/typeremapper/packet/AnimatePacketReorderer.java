package protocolsupport.protocol.typeremapper.packet;

import protocolsupport.protocol.PacketDataCodecImpl.ServerBoundPacketDataProcessor;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public class AnimatePacketReorderer extends ServerBoundPacketDataProcessor {

	protected ServerBoundPacketData animatePacket;

	@Override
	public void process(ServerBoundPacketData packet) {
		ServerBoundPacketType packetType = packet.getPacketType();

		if (animatePacket != null) {
			if (packetType == ServerBoundPacketType.SERVERBOUND_PLAY_USE_ENTITY) {
				read(packet);
				read(animatePacket);
				animatePacket = null;
				return;
			} else {
				read(animatePacket);
				animatePacket = null;
			}
		}

		if (packetType == ServerBoundPacketType.SERVERBOUND_PLAY_ANIMATION) {
			animatePacket = packet;
		} else {
			read(packet);
		}
	}

	@Override
	public void release() {
		if (animatePacket != null) {
			animatePacket.release();
		}
	}

}
