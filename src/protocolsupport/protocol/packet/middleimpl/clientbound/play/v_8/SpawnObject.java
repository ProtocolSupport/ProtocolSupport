package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject {

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntityType type = entityRemapper.getRemappedEntityType();
		objectdata = entityObjectDataRemappingTable.getRemap(type).applyAsInt(objectdata);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_OBJECT_ID);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		serializer.writeByte(LegacyEntityId.getObjectIntId(type));
		serializer.writeInt((int) (x * 32));
		serializer.writeInt((int) (y * 32));
		serializer.writeInt((int) (z * 32));
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeInt(objectdata);
		if (objectdata > 0) {
			serializer.writeShort(motX);
			serializer.writeShort(motY);
			serializer.writeShort(motZ);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
