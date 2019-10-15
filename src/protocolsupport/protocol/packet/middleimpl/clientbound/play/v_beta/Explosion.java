package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Explosion extends MiddleExplosion {

	public Explosion(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();

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
		packets.add(explosion);

		packets.add(EntityVelocity.create(cache.getWatchedEntityCache().getSelfPlayerEntityId(), (int) (pMotX * 8000), (int) (pMotY * 8000), (int) (pMotZ * 8000)));

		return packets;
	}

}
