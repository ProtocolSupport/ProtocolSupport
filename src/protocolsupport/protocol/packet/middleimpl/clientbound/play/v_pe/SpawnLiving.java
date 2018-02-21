package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnLiving extends MiddleSpawnLiving {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		return RecyclableSingletonList.create(create(
			version,
			entity, x, y, z,
			motX / 8.000F, motY / 8000.F, motZ / 8000.F, pitch, yaw, cache.getLocale(),
			null, PEDataValues.getLivingEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType()))
		));
	}

	public static ClientBoundPacketData createSimple(ProtocolVersion version,
			int entityId, double x, double y, double z, int peEntityType) {
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
			float pitch, float yaw, String locale, ArrayMap<DataWatcherObject<?>> metadata, int entityType) {
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
		VarNumberSerializer.writeVarInt(serializer, 0); //attributes, not used
		if (metadata == null) {
			VarNumberSerializer.writeVarInt(serializer, 0);
		} else {
			EntityMetadata.encodeMeta(serializer, version, locale, EntityMetadata.transform(entity, metadata, version));
		}
		VarNumberSerializer.writeVarInt(serializer, 0); //links, not used
		return serializer;
	}

}
