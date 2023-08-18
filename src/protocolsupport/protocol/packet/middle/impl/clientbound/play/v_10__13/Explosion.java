package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_10__13;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_10__18.WorldSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheMiddleExplosion;
import protocolsupport.protocol.typeremapper.basic.SoundTransformer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.SoundCategory;

public class Explosion extends AbstractChunkCacheMiddleExplosion implements
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13 {

	public Explosion(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(WorldSound.createCustomSound(
			version,
			(int) (x * 8), (int) (y * 8), (int) (z * 8),
			"entity.generic.explode", SoundCategory.BLOCKS,
			4.0F, SoundTransformer.createEntityGenericExplodePitch()
		));

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
