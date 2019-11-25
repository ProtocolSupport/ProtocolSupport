package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter;
import protocolsupport.protocol.typeremapper.legacy.LegacyRelMoveConverter.RelMove;

public class EntityRelMoveLook extends MiddleEntityRelMoveLook {

	public EntityRelMoveLook(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		int relMoveX = relX / 128;
		int relMoveY = relY / 128;
		int relMoveZ = relZ / 128;
		for (RelMove relMove : LegacyRelMoveConverter.getRelMoves(new RelMove(relMoveX, relMoveY, relMoveZ), 127)) {
			ClientBoundPacketData entityrelmovelook = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE_LOOK);
			entityrelmovelook.writeInt(entityId);
			entityrelmovelook.writeByte(relMove.getX());
			entityrelmovelook.writeByte(relMove.getY());
			entityrelmovelook.writeByte(relMove.getZ());
			entityrelmovelook.writeByte(yaw);
			entityrelmovelook.writeByte(pitch);
			codec.write(entityrelmovelook);
		}
	}

}
