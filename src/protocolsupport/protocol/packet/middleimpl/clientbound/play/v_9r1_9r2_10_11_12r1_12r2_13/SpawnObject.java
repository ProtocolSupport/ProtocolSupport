package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry;
import protocolsupport.protocol.typeremapper.basic.ObjectDataRemappersRegistry.ObjectDataRemappingTable;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public class SpawnObject extends MiddleSpawnObject {

	protected final ObjectDataRemappingTable entityObjectDataRemappingTable = ObjectDataRemappersRegistry.REGISTRY.getTable(version);

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient0(NetworkEntityType remappedEntityType) {
		if (remappedEntityType.isOfType(NetworkEntityType.MINECART)) {
			objectdata = LegacyEntityId.getMinecartObjectData(remappedEntityType);
		} else {
			objectdata = entityObjectDataRemappingTable.getRemap(remappedEntityType).applyAsInt(objectdata);
		}

		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT);
		VarNumberSerializer.writeVarInt(spawnobject, entity.getId());
		MiscSerializer.writeUUID(spawnobject, entity.getUUID());
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(remappedEntityType));
		spawnobject.writeDouble(x);
		spawnobject.writeDouble(y);
		spawnobject.writeDouble(z);
		spawnobject.writeByte(pitch);
		spawnobject.writeByte(yaw);
		spawnobject.writeInt(objectdata);
		spawnobject.writeShort(motX);
		spawnobject.writeShort(motY);
		spawnobject.writeShort(motZ);
		codec.write(spawnobject);
	}

}
