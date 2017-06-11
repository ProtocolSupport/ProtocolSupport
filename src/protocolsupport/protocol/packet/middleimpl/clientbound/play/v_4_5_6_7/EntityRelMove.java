package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyRelMoveConverter;
import protocolsupport.protocol.legacyremapper.LegacyRelMoveConverter.RelMove;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityRelMove extends MiddleEntityRelMove {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		int relMoveX = relX / 128;
		int relMoveY = relY / 128;
		int relMoveZ = relZ / 128;
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		for (RelMove relMove : LegacyRelMoveConverter.getRelMoves(new RelMove(relMoveX, relMoveY, relMoveZ), 127)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, version);
			serializer.writeInt(entityId);
			serializer.writeByte(relMove.getX());
			serializer.writeByte(relMove.getY());
			serializer.writeByte(relMove.getZ());
			packets.add(serializer);
		}
		return packets;
	}

}
