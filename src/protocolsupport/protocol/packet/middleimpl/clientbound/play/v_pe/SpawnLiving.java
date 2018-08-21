package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnLiving;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntitySetAttributes.AttributeInfo;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData;
import protocolsupport.protocol.typeremapper.pe.PEDataValues.PEEntityData.Offset;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.ObjectFloatTuple;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SpawnLiving extends MiddleSpawnLiving {

	public SpawnLiving(ConnectionImpl connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(create(
			version, cache.getAttributesCache().getLocale(),
			entity, x, y, z,
			motX / 8.000F, motY / 8000.F, motZ / 8000.F, pitch, yaw, headPitch,
			metadata.getRemapped()
		));
		if (entity.getType() == NetworkEntityType.PIG) {
			packets.add(EntitySetAttributes.create(version, entity, new ObjectFloatTuple<>(AttributeInfo.HORSE_JUMP_STRENGTH, 0.432084373616155F)));
		}
		DataWatcherObjectIndex.EntityLiving.HEALTH.getValue(metadata.getOriginal()).ifPresent(healthWatcher -> {
			packets.add(EntitySetAttributes.create(version, entity, new ObjectFloatTuple<>(AttributeInfo.HEALTH, healthWatcher.getValue())));
		});
		return packets;
	}

	public static ClientBoundPacketData createSimple(ProtocolVersion version, String locale, NetworkEntity entity, double x, double y, double z) {
		return create(version, locale, entity, x, y, z, 0, 0, 0, 0, 0, 0, null);
	}

	public static ClientBoundPacketData create(
		ProtocolVersion version, String locale,
		NetworkEntity entity, double x, double y, double z,
		float motX, float motY, float motZ,
		float pitch, float yaw, float headYaw,
		ArrayMap<DataWatcherObject<?>> metadata
	) {
		PEEntityData typeData = PEDataValues.getEntityData(entity.getType());
		if ((typeData != null) && (typeData.getOffset() != null)) {
			Offset offset = typeData.getOffset();
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			pitch += offset.getPitch();
			yaw += offset.getYaw();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_ENTITY);
		VarNumberSerializer.writeSVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarInt(serializer, PEDataValues.getEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType())));
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
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
			EntityMetadata.encodeMeta(serializer, version, locale, EntityMetadata.transform(entity, metadata, version));
		}
		VarNumberSerializer.writeVarInt(serializer, 0); //links, sent in separate packet
		return serializer;
	}

}
