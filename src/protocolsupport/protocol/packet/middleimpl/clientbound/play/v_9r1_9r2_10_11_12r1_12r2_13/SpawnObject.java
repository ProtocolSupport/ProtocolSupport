package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject {

	protected final ObjectDataRemappingTable entityObjectDataRemappingTable = ObjectDataRemappersRegistry.REGISTRY.getTable(version);

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		NetworkEntityType type = entityRemapper.getRemappedEntityType();
		if (type.isOfType(NetworkEntityType.MINECART)) {
			objectdata = LegacyEntityId.getMinecartObjectData(type);
		} else {
			objectdata = entityObjectDataRemappingTable.getRemap(type).applyAsInt(objectdata);
		}

		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		MiscSerializer.writeUUID(serializer, entity.getUUID());
		serializer.writeByte(LegacyEntityId.getObjectIntId(type));
		serializer.writeDouble(x);
		serializer.writeDouble(y);
		serializer.writeDouble(z);
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeInt(objectdata);
		serializer.writeShort(motX);
		serializer.writeShort(motY);
		serializer.writeShort(motZ);
		return RecyclableSingletonList.create(serializer);
	}

}
