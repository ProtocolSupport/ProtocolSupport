package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter.RelMove;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityRelMove extends MiddleEntityRelMove {
	protected void addRemainder() {
		NetworkEntity watchedEntity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (watchedEntity != null) {
			Position remainderPos = watchedEntity.getRelRemainderPosition();
			relX += remainderPos.getX();
			relY += remainderPos.getY();
			relZ += remainderPos.getZ();
			remainderPos.setX(relX % 128);
			remainderPos.setY(relY % 128);
			remainderPos.setZ(relZ % 128);
		}
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		addRemainder();
		int relMoveX = relX / 128;
		int relMoveY = relY / 128;
		int relMoveZ = relZ / 128;
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		for (RelMove relMove : LegacyRelMoveConverter.getRelMoves(new RelMove(relMoveX, relMoveY, relMoveZ), 127)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID);
			serializer.writeInt(entityId);
			serializer.writeByte(relMove.getX());
			serializer.writeByte(relMove.getY());
			serializer.writeByte(relMove.getZ());
			packets.add(serializer);
		}
		return packets;
	}

}
