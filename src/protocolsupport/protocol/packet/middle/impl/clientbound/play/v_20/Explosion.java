package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class Explosion extends MiddleExplosion implements
IClientboundMiddlePacketV20 {

	public Explosion(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData explosionPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_EXPLOSION);
		explosionPacket.writeDouble(x);
		explosionPacket.writeDouble(y);
		explosionPacket.writeDouble(z);
		explosionPacket.writeFloat(radius);
		ArrayCodec.writeVarIntTArray(explosionPacket, blocks, (blockTo, block) -> {
			blockTo.writeByte(block.getX());
			blockTo.writeByte(block.getY());
			blockTo.writeByte(block.getZ());
		});
		explosionPacket.writeFloat(pMotX);
		explosionPacket.writeFloat(pMotY);
		explosionPacket.writeFloat(pMotZ);
		io.writeClientbound(explosionPacket);
	}

}
