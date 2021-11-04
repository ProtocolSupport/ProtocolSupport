package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6;

import java.util.Collections;

import protocolsupport.protocol.codec.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.codec.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6.SpawnObject;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7.EntityVelocity;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityMetadata;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6_7.EntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6_7_8.EntityPassengers;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.utils.Vector3S;

public class EntityMetadata extends AbstractPotionItemAsObjectDataEntityMetadata implements IClientboundMiddlePacketV6 {

	public EntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		entitymetadataPacket.writeInt(entity.getId());
		NetworkEntityMetadataSerializer.writeLegacyData(entitymetadataPacket, version, clientCache.getLocale(), remappedMetadata);
		io.writeClientbound(entitymetadataPacket);
	}

	@Override
	protected void writePlayerUseBed(Position position) {
		ClientBoundPacketData usebedPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED);
		usebedPacket.writeInt(entity.getId());
		usebedPacket.writeByte(0);
		PositionCodec.writePositionIBI(usebedPacket, position);
		io.writeClientbound(usebedPacket);
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
			entity -> AbstractPassengerStackEntityPassengers.writeVehiclePassengers(io, EntityPassengers::create, entity, Collections.emptyList()),
			entity -> AbstractPassengerStackEntityPassengers.writeLeaveVehicleConnectStack(io, EntityPassengers::create, entity),
			entity -> EntityDestroy.writeDestroyEntity(io, entity)
		);
		io.writeClientbound(SpawnObject.create(
			entity.getId(), entityLegacyFormatTable.get(lType).getType(),
			ecache.getX(), ecache.getY(), ecache.getZ(),
			ecache.getPitchB(), ecache.getYawB(),
			objectdata,
			velocity.getX(), velocity.getY(), velocity.getZ()
		));
		if (objectdata <= 0) {
			io.writeClientbound(EntityVelocity.create(entity.getId(), velocity.getX(), velocity.getY(), velocity.getZ()));
		}
	}

}
