package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.minecraftdata.PocketData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData;
import protocolsupport.protocol.utils.minecraftdata.PocketData.PocketEntityData.PocketOffset;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SpawnLiving extends MiddleSpawnLiving {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(create(
			version,
			entity, x, y, z,
			motX / 8.000F, motY / 8000.F, motZ / 8000.F, pitch, yaw, cache.getAttributesCache().getLocale(),
			metadata.getRemapped(), PEDataValues.getLivingEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType()))
		));
		DataWatcherObject<?> healthWatcher = metadata.getOriginal().get(DataWatcherObjectIndex.EntityLiving.HEALTH);
		if (healthWatcher != null) {
			packets.add(EntitySetAttributes.create(version, entity, EntitySetAttributes.createAttribute("minecraft:health", (Float) healthWatcher.getValue())));
		}
		return packets;
	}

	public static ClientBoundPacketData createSimple(ProtocolVersion version,
			long entityId, double x, double y, double z, int peEntityType) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_ENTITY, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		VarNumberSerializer.writeVarInt(serializer, peEntityType);
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
		serializer.writeFloatLE(0);
		serializer.writeFloatLE(0);
		serializer.writeFloatLE(0);
		serializer.writeFloatLE(0);
		serializer.writeFloatLE(0);
		VarNumberSerializer.writeVarInt(serializer, 0);
		VarNumberSerializer.writeVarInt(serializer, 0);
		VarNumberSerializer.writeVarInt(serializer, 0);
		return serializer;
	}

	public static ClientBoundPacketData create(ProtocolVersion version,
			NetworkEntity entity, double x, double y, double z,
			float motX, float motY, float motZ,
			float pitch, float yaw, 
			String locale, ArrayMap<DataWatcherObject<?>> metadata, int entityType
		) {
		PocketEntityData typeData = PocketData.getPocketEntityData(entity.getType());
		if (typeData != null && typeData.getOffset() != null) {
			PocketOffset offset = typeData.getOffset();
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			pitch += offset.getPitch();
			yaw += offset.getYaw();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_ENTITY, version);
		VarNumberSerializer.writeSVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarInt(serializer, entityType);
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
		serializer.writeFloatLE(motX);
		serializer.writeFloatLE(motY);
		serializer.writeFloatLE(motZ);
		serializer.writeFloatLE(pitch);
		serializer.writeFloatLE(yaw);
		VarNumberSerializer.writeVarInt(serializer, 0); //attributes, send in separate packet
		if (metadata == null) {
			VarNumberSerializer.writeVarInt(serializer, 0);
		} else {
			EntityMetadata.encodeMeta(serializer, version, locale, EntityMetadata.transform(entity, metadata, version));
		}
		VarNumberSerializer.writeVarInt(serializer, 0); //links, send in separate packet
		return serializer;
	}

}
