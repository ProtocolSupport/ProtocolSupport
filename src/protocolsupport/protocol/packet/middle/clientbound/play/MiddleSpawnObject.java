package protocolsupport.protocol.packet.middle.clientbound.play;

import java.text.MessageFormat;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;

public abstract class MiddleSpawnObject extends ClientBoundMiddlePacket {

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	public MiddleSpawnObject(MiddlePacketInit init) {
		super(init);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected byte pitch;
	protected byte yaw;
	protected int objectdata;
	protected short velX;
	protected short velY;
	protected short velZ;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = UUIDSerializer.readUUID2L(serverdata);
		int typeId = serverdata.readUnsignedByte();
		NetworkEntityType type = NetworkEntityType.getObjectByNetworkTypeId(typeId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		pitch = serverdata.readByte();
		yaw = serverdata.readByte();
		objectdata = serverdata.readInt();
		velX = serverdata.readShort();
		velY = serverdata.readShort();
		velZ = serverdata.readShort();

		if (type == NetworkEntityType.NONE) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(MessageFormat.format(
					"Attempted to spawn unknown object entity type id {0} at {1},{2},{3}",
					typeId, x, y, z
				));
			}
			throw CancelMiddlePacketException.INSTANCE;
		}

		entity = NetworkEntity.createObject(uuid, entityId, type);
	}

	@Override
	protected void handleReadData() {
		NetworkEntityDataCache ecache = entity.getDataCache();
		ecache.setLocation(x, y, z, pitch, yaw);
		ecache.setHeadYaw(yaw);

		entityCache.addEntity(entity);
	}

}
