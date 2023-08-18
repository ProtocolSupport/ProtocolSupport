package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import java.util.Collections;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractLegacyPotionItemEntityMetadata;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6__8.EntityPassengers;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.utils.Vector3S;

public class EntityMetadata extends AbstractLegacyPotionItemEntityMetadata implements IClientboundMiddlePacketV8 {

	public EntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		VarNumberCodec.writeVarInt(entitymetadataPacket, entity.getId());
		NetworkEntityMetadataCodec.writeLegacyData(entitymetadataPacket, version, clientCache.getLocale(), remappedMetadata);
		io.writeClientbound(entitymetadataPacket);
	}

	@Override
	protected void writePlayerUseBed(Position position) {
		ClientBoundPacketData usebedPacket = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_USE_BED);
		VarNumberCodec.writeVarInt(usebedPacket, entity.getId());
		PositionCodec.writePositionLXYZ(usebedPacket, position);
		io.writeClientbound(usebedPacket);
	}

	@Override
	protected void writePotionRespawn(NetworkItemStack item, Vector3S velocity) {
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
		io.writeClientbound(SpawnEntity.createSpawnObject(
			entity.getId(), fType,
			ecache.getX(), ecache.getY(), ecache.getZ(),
			ecache.getPitchB(), ecache.getYawB(),
			objectdata,
			velocity.getX(), velocity.getY(), velocity.getZ()
		));
		if (objectdata >= 0) {
			io.writeClientbound(EntityVelocity.create(entity.getId(), velocity.getX(), velocity.getY(), velocity.getZ()));
		}
	}

	@Override
	protected void writePaintingRespawn(Position position, int direction, int variant) {
		AbstractPassengerStackEntityDestroy.writeDestroy(
			entity,
			entity -> AbstractPassengerStackEntityPassengers.writeVehiclePassengers(io, EntityPassengers::create, entity, Collections.emptyList()),
			entity -> AbstractPassengerStackEntityPassengers.writeLeaveVehicleConnectStack(io, EntityPassengers::create, entity),
			entity -> EntityDestroy.writeDestroyEntity(io, entity)
		);

		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_PAINTING);
		VarNumberCodec.writeVarInt(spawnpainting, entity.getId());
		StringCodec.writeVarIntUTF8String(spawnpainting, LegacyPainting.getName(variant));
		PositionCodec.writePositionLXYZ(spawnpainting, position);
		spawnpainting.writeByte(direction);
		io.writeClientbound(spawnpainting);
	}

}
