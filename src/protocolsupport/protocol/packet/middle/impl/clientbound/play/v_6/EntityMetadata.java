package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6;

import java.util.Collections;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7.EntityVelocity;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractLegacyPotionItemEntityMetadata;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6__7.EntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6__8.EntityPassengers;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7.SpawnEntity;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyPainting;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.utils.Vector3S;

public class EntityMetadata extends AbstractLegacyPotionItemEntityMetadata implements IClientboundMiddlePacketV6 {

	public EntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_METADATA);
		entitymetadataPacket.writeInt(entity.getId());
		NetworkEntityMetadataCodec.writeLegacyData(entitymetadataPacket, version, clientCache.getLocale(), remappedMetadata);
		io.writeClientbound(entitymetadataPacket);
	}

	@Override
	protected void writePlayerUseBed(Position position) {
		ClientBoundPacketData usebedPacket = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_USE_BED);
		usebedPacket.writeInt(entity.getId());
		usebedPacket.writeByte(0);
		PositionCodec.writePositionIBI(usebedPacket, position);
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
			entity.getId(), entityLegacyFormatTable.get(lType).getType(),
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

		switch (direction) {
			case 0: {
				position.modifyZ(-1);
				break;
			}
			case 1: {
				position.modifyX(1);
				break;
			}
			case 2: {
				position.modifyZ(1);
				break;
			}
			case 3: {
				position.modifyX(-1);
				break;
			}
		}
		ClientBoundPacketData spawnpainting = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_SPAWN_PAINTING);
		spawnpainting.writeInt(entity.getId());
		StringCodec.writeShortUTF16BEString(spawnpainting, LegacyPainting.getName(variant));
		PositionCodec.writePositionIII(spawnpainting, position);
		spawnpainting.writeInt(direction);
		io.writeClientbound(spawnpainting);
	}

}
