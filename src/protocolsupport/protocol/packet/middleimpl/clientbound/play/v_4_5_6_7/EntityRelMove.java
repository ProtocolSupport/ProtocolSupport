package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter.RelMove;

public class EntityRelMove extends MiddleEntityRelMove {

	public EntityRelMove(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		int relMoveX = relX / 128;
		int relMoveY = relY / 128;
		int relMoveZ = relZ / 128;
		for (RelMove relMove : LegacyRelMoveConverter.getRelMoves(new RelMove(relMoveX, relMoveY, relMoveZ), 127)) {
			ClientBoundPacketData entityrelmove = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE);
			entityrelmove.writeInt(entityId);
			entityrelmove.writeByte(relMove.getX());
			entityrelmove.writeByte(relMove.getY());
			entityrelmove.writeByte(relMove.getZ());
			codec.write(entityrelmove);
		}
	}
}
