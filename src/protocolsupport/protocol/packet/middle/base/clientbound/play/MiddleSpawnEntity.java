package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.text.MessageFormat;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.ProtocolSupportFileLog;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;

public abstract class MiddleSpawnEntity extends ClientBoundMiddlePacket {

	protected MiddleSpawnEntity(IMiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected byte pitch;
	protected byte yaw;
	protected byte headYaw;
	protected int objectdata;
	protected short motX;
	protected short motY;
	protected short motZ;

	@Override
	protected void decode(ByteBuf serverdata) {
		int entityId = VarNumberCodec.readVarInt(serverdata);
		UUID uuid = UUIDCodec.readUUID(serverdata);
		int typeId = VarNumberCodec.readVarInt(serverdata);
		NetworkEntityType type = NetworkEntityType.getByNetworkTypeId(typeId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		pitch = serverdata.readByte();
		yaw = serverdata.readByte();
		headYaw = serverdata.readByte();
		objectdata = VarNumberCodec.readVarInt(serverdata);
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();

		if (type == NetworkEntityType.NONE) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(createInvalidEntityMessage(typeId));
			}
			if (ProtocolSupportFileLog.isEnabled()) {
				ProtocolSupportFileLog.logWarningMessage(createInvalidEntityMessage(typeId));
			}
			throw MiddlePacketCancelException.INSTANCE;
		}

		entity = NetworkEntity.createMob(uuid, entityId, type);

		NetworkEntityDataCache ecache = entity.getDataCache();
		ecache.setLocation(x, y, z, pitch, yaw);
		ecache.setHeadYaw(yaw);
		entityCache.addEntity(entity);
	}

	private String createInvalidEntityMessage(int typeId) {
		return MessageFormat.format(
			"Invalid mob entity type id {0} (x: {1}, y: {2}, z: {3})",
			typeId, x, y, z
		);
	}

}
