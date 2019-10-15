package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2.AbstractLocationOffsetSpawnObject;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityItemDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends AbstractLocationOffsetSpawnObject {

	protected final ObjectDataRemappingTable entityObjectDataRemappingTable = ObjectDataRemappersRegistry.REGISTRY.getTable(version);

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (entity.getType() == NetworkEntityType.ITEM_FRAME) {
			//TODO: spawn displayed item instead?
			return RecyclableEmptyList.get();
		}
		if (entity.getType() == NetworkEntityType.ITEM) {
			((NetworkEntityItemDataCache) entity.getDataCache()).setSpawnCoords(x, y, z);
			return RecyclableEmptyList.get();
		}
		if (entity.getType() == NetworkEntityType.ENDER_CRYSTAL) {
			//TODO: spawn falling block with some item instead?
			return RecyclableEmptyList.get();
		}
		NetworkEntityType type = entityRemapper.getRemappedEntityType();
		if (type.isOfType(NetworkEntityType.MINECART)) {
			objectdata = LegacyEntityId.getMinecartObjectData(type);
		} else {
			objectdata = entityObjectDataRemappingTable.getRemap(type).applyAsInt(objectdata);
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT);
		serializer.writeInt(entity.getId());
		serializer.writeByte(type.getNetworkTypeId());
		serializer.writeInt((int) x * 32);
		serializer.writeInt((int) y * 32);
		serializer.writeInt((int) z * 32);
		serializer.writeInt(objectdata);
		if (objectdata > 0) {
			serializer.writeShort(motX);
			serializer.writeShort(motY);
			serializer.writeShort(motZ);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
