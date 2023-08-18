package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1__16r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_10__18.WorldSound;
import protocolsupport.protocol.typeremapper.basic.SoundTransformer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.SoundCategory;

public class Explosion extends MiddleExplosion implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2 {

	public Explosion(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_14_4)) {
			io.writeClientbound(WorldSound.createCustomSound(
				version,
				(int) (x * 8), (int) (y * 8), (int) (z * 8),
				"entity.generic.explode", SoundCategory.BLOCKS,
				4.0F, SoundTransformer.createEntityGenericExplodePitch()
			));
		}

		ClientBoundPacketData explosion = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_EXPLOSION);
		explosion.writeFloat((float) x);
		explosion.writeFloat((float) y);
		explosion.writeFloat((float) z);
		explosion.writeFloat(radius);
		explosion.writeInt(blocks.length);
		for (Position block : blocks) {
			explosion.writeByte(block.getX());
			explosion.writeByte(block.getY());
			explosion.writeByte(block.getZ());
		}
		explosion.writeFloat(pMotX);
		explosion.writeFloat(pMotY);
		explosion.writeFloat(pMotZ);
		io.writeClientbound(explosion);
	}

}
