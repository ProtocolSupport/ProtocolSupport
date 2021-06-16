package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import java.util.Collections;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6.SpawnObject;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7.EntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityMetadata;
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
import protocolsupport.utils.Vector3S;

public class EntityMetadata extends AbstractPotionItemAsObjectDataEntityMetadata {

	public EntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		entitymetadataPacket.writeInt(entityId);
		NetworkEntityMetadataSerializer.writeLegacyData(entitymetadataPacket, version, clientCache.getLocale(), remappedMetadata);
		codec.writeClientbound(entitymetadataPacket);
	}

	@Override
	protected void writePlayerUseBed(Position position) {
		ClientBoundPacketData usebedPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED);
		usebedPacket.writeInt(entityId);
		usebedPacket.writeByte(0);
		PositionSerializer.writeLegacyPositionB(usebedPacket, position);
		codec.writeClientbound(usebedPacket);
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
			entity,
			entity -> AbstractPassengerStackEntityPassengers.writeVehiclePassengers(codec, EntityPassengers::create, entity, Collections.emptyList()),
			entity -> AbstractPassengerStackEntityPassengers.writeLeaveVehicleConnectStack(codec, EntityPassengers::create, entity),
			entity -> EntityDestroy.writeDestroyEntity(codec, entity)
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
