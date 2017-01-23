package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class MiddleSpawnNamed<T> extends ClientBoundMiddlePacket<T> {

	protected int playerEntityId;
	protected UUID uuid;
	protected String name;
	protected double x;
	protected double y;
	protected double z;
	protected int yaw;
	protected int pitch;
	protected List<ProfileProperty> properties;
	protected WatchedEntity wplayer;
	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		playerEntityId = serializer.readVarInt();
		uuid = serializer.readUUID();
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readUnsignedByte();
		pitch = serializer.readUnsignedByte();
		metadata = DataWatcherDeserializer.decodeData(serializer);
	}

	@Override
	public void handle() {
		wplayer = new WatchedPlayer(playerEntityId);
		cache.addWatchedEntity(wplayer);
		NetworkDataCache.PlayerListEntry entry = cache.getPlayerListEntry(uuid);
		if (entry != null) {
			name = entry.getUserName();
			properties = entry.getProperties().getAll(true);
		} else {
			name = "Unknown";
			properties = Collections.emptyList();
		}
	}

}
