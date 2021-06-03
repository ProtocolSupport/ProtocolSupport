package protocolsupport.protocol.packet.middle.clientbound.play;

import java.text.MessageFormat;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.ProtocolSupportFileLog;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.zplatform.ServerPlatform;

public abstract class MiddleSpawnNamed extends ClientBoundMiddlePacket {

	protected final PlayerListCache playerlistCache = cache.getPlayerListCache();
	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected MiddleSpawnNamed(MiddlePacketInit init) {
		super(init);
	}

	protected NetworkEntity entity;
	protected PlayerListEntry playerlistEntry;
	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;

	@Override
	protected void decode(ByteBuf serverdata) {
		int playerEntityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = UUIDSerializer.readUUID2L(serverdata);
		entity = NetworkEntity.createPlayer(uuid, playerEntityId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();

		playerlistEntry = playerlistCache.getEntry(uuid);
		if (playerlistEntry == null) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(createInvalidEntityMessage(uuid));
			}
			if (ProtocolSupportFileLog.isEnabled()) {
				ProtocolSupportFileLog.logWarningMessage(createInvalidEntityMessage(uuid));
			}
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

	private String createInvalidEntityMessage(UUID uuid) {
		return MessageFormat.format(
			"Attempted to spawn unknown (not added to playerlist) named entity (uid: {0}, x: {1}, y: {2}, z: {3})",
			uuid, x, y, z
		);
	}

	@Override
	protected void handle() {
		NetworkEntityDataCache ecache = entity.getDataCache();
		ecache.setLocation(x, y, z, pitch, yaw);
		ecache.setHeadYaw(yaw);

		entityCache.addEntity(entity);
	}

}
