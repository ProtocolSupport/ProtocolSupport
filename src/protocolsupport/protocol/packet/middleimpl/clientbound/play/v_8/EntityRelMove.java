package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter.RelMove;

public class EntityRelMove extends MiddleEntityRelMove {

	public EntityRelMove(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		int relMoveX = relX / 128;
		int relMoveY = relY / 128;
		int relMoveZ = relZ / 128;
		for (RelMove relMove : LegacyRelMoveConverter.getRelMoves(new RelMove(relMoveX, relMoveY, relMoveZ), 127)) {
			ClientBoundPacketData entityrelmove = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE);
			VarNumberSerializer.writeVarInt(entityrelmove, entityId);
			entityrelmove.writeByte(relMove.getX());
			entityrelmove.writeByte(relMove.getY());
			entityrelmove.writeByte(relMove.getZ());
			entityrelmove.writeBoolean(onGround);
			codec.write(entityrelmove);
		}
	}

}
