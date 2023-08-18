package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__18;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class Explosion extends MiddleExplosion implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public Explosion(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData explosionPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_EXPLOSION);
		explosionPacket.writeFloat((float) x);
		explosionPacket.writeFloat((float) y);
		explosionPacket.writeFloat((float) z);
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
