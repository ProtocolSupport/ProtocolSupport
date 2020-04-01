package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.Position;

public class Explosion extends MiddleExplosion {

	public Explosion(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData explosion = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_EXPLOSION);
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
		codec.write(explosion);

		codec.write(EntityVelocity.create(cache.getWatchedEntityCache().getSelfPlayerEntityId(), (int) (pMotX * 8000), (int) (pMotY * 8000), (int) (pMotZ * 8000)));
	}

}
