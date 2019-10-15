package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter.RelMove;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityRelMoveLook extends MiddleEntityRelMoveLook {

	public EntityRelMoveLook(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		int relMoveX = relX / 128;
		int relMoveY = relY / 128;
		int relMoveZ = relZ / 128;
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		for (RelMove relMove : LegacyRelMoveConverter.getRelMoves(new RelMove(relMoveX, relMoveY, relMoveZ), 127)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE_LOOK);
			VarNumberSerializer.writeVarInt(serializer, entityId);
			serializer.writeByte(relMove.getX());
			serializer.writeByte(relMove.getY());
			serializer.writeByte(relMove.getZ());
			serializer.writeByte(yaw);
			serializer.writeByte(pitch);
			serializer.writeBoolean(onGround);
			packets.add(serializer);
		}
		return packets;
	}

}
