package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.WorldCustomSound;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.SoundCategory;

public class Explosion extends MiddleExplosion {

	public Explosion(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_14_4)) {
			codec.write(WorldCustomSound.create(
				version,
				(int) (x * 8), (int) (y * 8), (int) (z * 8),
				"entity.generic.explode", SoundCategory.BLOCKS,
				4.0F, SoundRemapper.createEntityGenericExplodePitch()
			));
		}

		ClientBoundPacketData explosion = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_EXPLOSION);
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
		codec.write(explosion);
	}

}
