package protocolsupport.protocol.typeremapper.packet;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.ConnectionImpl.ServerboundPacketProcessor;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.IPacketData;

public class AnimatePacketReorderer extends ServerboundPacketProcessor {

	public AnimatePacketReorderer(ConnectionImpl connection) {
		super(connection);
	}

	protected IPacketData animatePacket;

	@Override
	public void process(IPacketData packet) {
		PacketType packetType = packet.getPacketType();

		if (animatePacket != null) {
			if (packetType == PacketType.SERVERBOUND_PLAY_USE_ENTITY) {
				read(packet);
				read(animatePacket);
				animatePacket = null;
				return;
			} else {
				read(animatePacket);
				animatePacket = null;
			}
		}

		if (packetType == PacketType.SERVERBOUND_PLAY_ANIMATION) {
			animatePacket = packet;
		} else {
			read(packet);
		}
	}

	public void release() {
		if (animatePacket != null) {
			animatePacket.recycle();
		}
	}

}
