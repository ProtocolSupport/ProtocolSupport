package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2.AbstractLocationOffsetSpawnObject;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class SpawnObject extends AbstractLocationOffsetSpawnObject {

	protected final ObjectDataRemappingTable entityObjectDataRemappingTable = ObjectDataRemappersRegistry.REGISTRY.getTable(version);

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		NetworkEntityType type = entityRemapper.getRemappedEntityType();
		if (type.isOfType(NetworkEntityType.MINECART)) {
			objectdata = LegacyEntityId.getMinecartObjectData(type);
		} else {
			objectdata = entityObjectDataRemappingTable.getRemap(type).applyAsInt(objectdata);
		}
		x *= 32;
		y *= 32;
		z *= 32;
		if (type == NetworkEntityType.ITEM_FRAME) {
			switch (objectdata) {
				case 0: {
					z -= 32;
					yaw = (byte) 128;
					break;
				}
				case 1: {
					x += 32;
					yaw = 64;
					break;
				}
				case 2: {
					z += 32;
					yaw = 0;
					break;
				}
				case 3: {
					x -= 32;
					yaw = (byte) 192;
					break;
				}
			}
		}

		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT);
		VarNumberSerializer.writeVarInt(spawnobject, entity.getId());
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(type));
		spawnobject.writeInt((int) x);
		spawnobject.writeInt((int) y);
		spawnobject.writeInt((int) z);
		spawnobject.writeByte(pitch);
		spawnobject.writeByte(yaw);
		spawnobject.writeInt(objectdata);
		if (objectdata > 0) {
			spawnobject.writeShort(motX);
			spawnobject.writeShort(motY);
			spawnobject.writeShort(motZ);
		}
		codec.write(spawnobject);
	}

}
