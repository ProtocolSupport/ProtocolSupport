package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLocationOffsetSpawnObject;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityItemDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class SpawnObject extends AbstractLocationOffsetSpawnObject {

	protected final ObjectDataRemappingTable entityObjectDataRemappingTable = ObjectDataRemappersRegistry.REGISTRY.getTable(version);

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClientAfterOffset(NetworkEntityType remappedEntityType) {
		switch (entity.getType()) {
			case ITEM_FRAME: {
				//TODO: spawn displayed item instead?
				return;
			}
			case ENDER_CRYSTAL: {
				//TODO: spawn falling block with some item instead?
				return;
			}
			case ITEM: {
				if (objectdata > 0) {
					((NetworkEntityItemDataCache) entity.getDataCache()).setPositionAndMotion(x, y, z, motX, motY, motZ);
				} else {
					((NetworkEntityItemDataCache) entity.getDataCache()).setPositionAndMotion(x, y, z, 0, 0, 0);
				}
				return;
			}
			default: {
				if (remappedEntityType.isOfType(NetworkEntityType.MINECART)) {
					objectdata = LegacyEntityId.getMinecartObjectData(remappedEntityType);
				} else {
					objectdata = entityObjectDataRemappingTable.getRemap(remappedEntityType).applyAsInt(objectdata);
				}
				ClientBoundPacketData spawnobject = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT);
				spawnobject.writeInt(entity.getId());
				spawnobject.writeByte(remappedEntityType.getNetworkTypeId());
				spawnobject.writeInt((int) x * 32);
				spawnobject.writeInt((int) y * 32);
				spawnobject.writeInt((int) z * 32);
				spawnobject.writeInt(objectdata);
				if (objectdata > 0) {
					spawnobject.writeShort(motX);
					spawnobject.writeShort(motY);
					spawnobject.writeShort(motZ);
				}
				codec.write(spawnobject);
				return;
			}
		}
	}

}
