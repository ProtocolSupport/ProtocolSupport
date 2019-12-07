package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
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
	public void writeToClient0() {
		if (entityRemappedType.isOfType(NetworkEntityType.MINECART)) {
			objectdata = LegacyEntityId.getMinecartObjectData(entityRemappedType);
		} else {
			objectdata = entityObjectDataRemappingTable.getRemap(entityRemappedType).applyAsInt(objectdata);
		}

		ClientBoundPacketData spawnobject = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT);
		VarNumberSerializer.writeVarInt(spawnobject, entity.getId());
		spawnobject.writeByte(LegacyEntityId.getObjectIntId(entityRemappedType));
		spawnobject.writeInt((int) (x * 32));
		spawnobject.writeInt((int) (y * 32));
		spawnobject.writeInt((int) (z * 32));
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
