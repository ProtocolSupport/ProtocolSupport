package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappingHelper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleSpawnLiving extends ClientBoundMiddlePacket {

	protected final WatchedEntityCache entityCache = cache.getWatchedEntityCache();

	public MiddleSpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected byte headPitch;
	protected int motX;
	protected int motY;
	protected int motZ;
	protected final ArrayMap<NetworkEntityMetadataObject<?>> metadata = new ArrayMap<>(EntityRemappersRegistry.MAX_METADATA_INDEX + 1);

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		int typeId = VarNumberSerializer.readVarInt(serverdata);
		entity = NetworkEntity.createMob(uuid, entityId, typeId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		headPitch = serverdata.readByte();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
		NetworkEntityMetadataSerializer.readDataTo(serverdata, metadata);
	}

	protected final EntityRemappingHelper entityRemapper = new EntityRemappingHelper(EntityRemappersRegistry.REGISTRY.getTable(version));

	@Override
	public void writeToClient() {
		if (!GenericIdSkipper.ENTITY.getTable(version).shouldSkip(entity.getType())) {
			entityCache.addWatchedEntity(entity);
			entityRemapper.remap(entity, metadata);
			writeToClient0();
		}
	}

	protected abstract void writeToClient0();

	@Override
	public void postHandle() {
		metadata.clear();
		entityRemapper.clear();
	}

}
