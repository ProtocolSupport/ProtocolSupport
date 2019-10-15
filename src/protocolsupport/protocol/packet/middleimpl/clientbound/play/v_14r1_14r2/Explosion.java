package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Explosion extends MiddleExplosion {

	public Explosion(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_EXPLOSION);
		serializer.writeFloat(x);
		serializer.writeFloat(y);
		serializer.writeFloat(z);
		serializer.writeFloat(radius);
		serializer.writeInt(blocks.length);
		for (Position block : blocks) {
			serializer.writeByte(block.getX());
			serializer.writeByte(block.getY());
			serializer.writeByte(block.getZ());
		}
		serializer.writeFloat(pMotX);
		serializer.writeFloat(pMotY);
		serializer.writeFloat(pMotZ);
		return RecyclableSingletonList.create(serializer);
	}

}
