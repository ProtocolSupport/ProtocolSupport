package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleExplosion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Explosion extends MiddleExplosion {

	public Explosion(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_EXPLOSION_ID);
		serializer.writeDouble(x);
		serializer.writeDouble(y);
		serializer.writeDouble(z);
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
