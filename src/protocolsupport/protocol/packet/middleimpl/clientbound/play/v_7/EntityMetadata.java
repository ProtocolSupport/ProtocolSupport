package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import java.util.Collections;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7.EntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8.EntityPassengers;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Vector3S;

public class EntityMetadata extends AbstractPotionItemAsObjectDataEntityMetadata {

	public EntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		entitymetadata.writeInt(entityId);
		NetworkEntityMetadataSerializer.writeLegacyData(entitymetadata, version, clientCache.getLocale(), remappedMetadata);
		codec.writeClientbound(entitymetadata);
	}

	@Override
	protected void writePlayerUseBed(Position position) {
		ClientBoundPacketData usebed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED);
		usebed.writeInt(entityId);
		PositionSerializer.writeLegacyPositionB(usebed, position);
		codec.writeClientbound(usebed);
	}

	@Override
	protected void writePotionReplaceSpawn(NetworkItemStack item, Vector3S velocity) {
		NetworkEntityDataCache ecache = entity.getDataCache();
		int objectdata = 0;
		if (!item.isNull()) {
			NBTCompound tag = item.getNBT();
			if (tag != null) {
				objectdata = LegacyPotionId.toLegacyId(CommonNBT.getPotionEffectType(tag), true);
			}
		}
		AbstractPassengerStackEntityDestroy.writeDestroy(
			Utils.createSingletonArrayList(entity),
			entity -> AbstractPassengerStackEntityPassengers.writeVehiclePassengers(codec, EntityPassengers::create, entity, Collections.emptyList()),
			entity -> AbstractPassengerStackEntityPassengers.writeLeaveVehicleConnectStack(codec, EntityPassengers::create, entity),
			entitiesIds -> EntityDestroy.writeDestroyEntities(codec, entitiesIds)
		);
		codec.writeClientbound(SpawnObject.create(
			entityId, entityLegacyFormatTable.get(lType).getType(),
			ecache.getX(), ecache.getY(), ecache.getZ(),
			ecache.getPitchB(), ecache.getYawB(),
			objectdata,
			velocity.getX(), velocity.getY(), velocity.getZ()
		));
		if (objectdata <= 0) {
			codec.writeClientbound(EntityVelocity.create(entityId, velocity.getX(), velocity.getY(), velocity.getZ()));
		}
	}

}
