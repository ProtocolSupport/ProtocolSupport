package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__6;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheMiddleExplosion;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.WorldSound;
import protocolsupport.protocol.typeremapper.basic.SoundTransformer;
import protocolsupport.protocol.types.Position;

public class Explosion extends AbstractChunkCacheMiddleExplosion implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6 {

	public Explosion(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(WorldSound.createCustomSound(
			version,
			x, y, z,
			"entity.generic.explode", 4.0F, SoundTransformer.createEntityGenericExplodePitch()
		));

		ClientBoundPacketData explosion = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_EXPLOSION);
		explosion.writeDouble(x);
		explosion.writeDouble(y);
		explosion.writeDouble(z);
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
