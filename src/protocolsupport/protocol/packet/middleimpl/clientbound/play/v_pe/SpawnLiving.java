package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntitySetAttributes.AttributeInfo;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData.Offset;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.ObjectFloatTuple;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SpawnLiving extends MiddleSpawnLiving {

	public SpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		PEEntityData typeData = PEDataValues.getEntityData(entity.getType());
		if (typeData != null && typeData.getOffset() != null) {
			Offset offset = typeData.getOffset();
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			pitch += offset.getPitch();
			yaw += offset.getYaw();
		}
		entity.getDataCache().setHeadRotation(headYaw);
		entity.getDataCache().setPos((float) x, (float) y, (float) z);
		entity.getDataCache().setYaw(yaw);
		entity.getDataCache().setPitch(pitch);
		packets.add(create(
			version, cache.getAttributesCache().getLocale(),
			entity, (float) x, (float) y, (float) z,
			motX / 8000.F, motY / 8000.F, motZ / 8000.F,
			pitch * 360.F / 256.F, yaw * 360.F / 256.F, headYaw * 360.F / 256.F,
			entityRemapper.getRemappedMetadata()
		));
		if (entity.getType() == NetworkEntityType.PIG) {
			packets.add(EntitySetAttributes.create(version, entity, new ObjectFloatTuple<>(AttributeInfo.HORSE_JUMP_STRENGTH, 0.432084373616155F)));
		}
		NetworkEntityMetadataObjectIndex.EntityLiving.HEALTH.getValue(entityRemapper.getOriginalMetadata()).ifPresent(healthWatcher -> {
			packets.add(EntitySetAttributes.create(version, entity, new ObjectFloatTuple<>(AttributeInfo.HEALTH, healthWatcher.getValue())));
		});
		return packets;
	}

	public static ClientBoundPacketData createSimple(ProtocolVersion version, String locale, NetworkEntity entity, float x, float y, float z) {
		return create(version, locale, entity, x, y, z, 0, 0, 0, 0, 0, 0, null);
	}

	public static ClientBoundPacketData create(
		ProtocolVersion version, String locale,
		NetworkEntity entity, float x, float y, float z,
		float motX, float motY, float motZ,
		float pitch, float yaw, float headYaw,
		ArrayMap<NetworkEntityMetadataObject<?>> metadata
	) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_ENTITY);
		VarNumberSerializer.writeSVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		NetworkEntityType entityType = EntityRemappersRegistry.REGISTRY.getTable(version).getRemap(entity.getType()).getLeft();
		StringSerializer.writeString(serializer, version, PEDataValues.getEntityKey(entityType));
		serializer.writeFloatLE(x);
		serializer.writeFloatLE(y);
		serializer.writeFloatLE(z);
		serializer.writeFloatLE(motX);
		serializer.writeFloatLE(motY);
		serializer.writeFloatLE(motZ);
		serializer.writeFloatLE(pitch);
		serializer.writeFloatLE(yaw);
		serializer.writeFloatLE(headYaw);
		VarNumberSerializer.writeVarInt(serializer, 0); //attributes, sent in separate packet
		if (metadata == null) {
			VarNumberSerializer.writeVarInt(serializer, 0);
		} else {
			DataWatcherSerializer.writePEData(serializer, version, locale, EntityMetadata.includeBaseFlags(version, metadata, entity));
		}
		VarNumberSerializer.writeVarInt(serializer, 0); //links, sent in separate packet
		return serializer;
	}

}
