package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class Explosion extends MiddleExplosion {

	public Explosion(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData explosionPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_EXPLOSION);
		explosionPacket.writeFloat(x);
		explosionPacket.writeFloat(y);
		explosionPacket.writeFloat(z);
		explosionPacket.writeFloat(radius);
		ArrayCodec.writeVarIntTArray(explosionPacket, blocks, (blockTo, block) -> {
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
