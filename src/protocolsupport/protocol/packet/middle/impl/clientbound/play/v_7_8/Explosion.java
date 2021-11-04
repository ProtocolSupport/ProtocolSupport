package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7_8;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.WorldCustomSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheMiddleExplosion;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.protocol.types.Position;

public class Explosion extends AbstractChunkCacheMiddleExplosion implements
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8 {

	public Explosion(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(WorldCustomSound.create(
			version,
			x, y, z,
			"entity.generic.explode",
			4.0F, SoundRemapper.createEntityGenericExplodePitch()
		));

		ClientBoundPacketData explosion = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_EXPLOSION);
		explosion.writeFloat(x);
		explosion.writeFloat(y);
		explosion.writeFloat(z);
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
