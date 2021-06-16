package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;

public class Explosion extends MiddleExplosion {

	public Explosion(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData explosionPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_EXPLOSION);
		explosionPacket.writeFloat(x);
		explosionPacket.writeFloat(y);
		explosionPacket.writeFloat(z);
		explosionPacket.writeFloat(radius);
		ArraySerializer.writeVarIntTArray(explosionPacket, blocks, (blockTo, block) -> {
			blockTo.writeByte(block.getX());
			blockTo.writeByte(block.getY());
			blockTo.writeByte(block.getZ());
		});
		explosionPacket.writeFloat(pMotX);
		explosionPacket.writeFloat(pMotY);
		explosionPacket.writeFloat(pMotZ);
		codec.writeClientbound(explosionPacket);
	}

}
