package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.util.Vector;

import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class MiddleSpawnNamed extends ClientBoundMiddlePacket {

	protected NetworkEntity entity;
	protected String name;
	protected double x;
	protected double y;
	protected double z;
	protected int yaw;
	protected int pitch;
	protected List<ProfileProperty> properties;
	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int playerEntityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		entity = NetworkEntity.createPlayer(uuid, playerEntityId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readUnsignedByte();
		pitch = serverdata.readUnsignedByte();
		metadata = DataWatcherDeserializer.decodeData(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

	@Override
	public void handle() {
		cache.addWatchedEntity(entity, new Vector(x, y, z), (byte) yaw, (byte) pitch);
		NetworkDataCache.PlayerListEntry entry = cache.getPlayerListEntry(entity.getUUID());
		if (entry != null) {
			name = entry.getUserName();
			properties = entry.getProperties().getAll(true);
		} else {
			name = "Unknown";
			properties = Collections.emptyList();
		}
	}

}
