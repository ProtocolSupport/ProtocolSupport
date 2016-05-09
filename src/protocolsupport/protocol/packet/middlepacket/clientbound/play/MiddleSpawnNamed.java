package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.properties.Property;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.LocalStorage.PlayerListEntry;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleSpawnNamed<T> extends ClientBoundMiddlePacket<T> {

	protected int playerEntityId;
	protected UUID uuid;
	protected String name;
	protected double x;
	protected double y;
	protected double z;
	protected int yaw;
	protected int pitch;
	protected List<Property> properties;
	protected WatchedEntity wplayer;
	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		playerEntityId = serializer.readVarInt();
		uuid = serializer.i();
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readUnsignedByte();
		pitch = serializer.readUnsignedByte();
		metadata = DataWatcherDeserializer.decodeData(ChannelUtils.toArray(serializer));
	}

	@Override
	public void handle() {
		wplayer = new WatchedPlayer(playerEntityId);
		storage.addWatchedEntity(wplayer);
		PlayerListEntry entry = storage.getPlayerListEntry(uuid);
		if (entry != null) {
			name = entry.getUserName();
			properties = entry.getProperties().getAll(true);
		} else {
			name = "Unknown";
			properties = Collections.emptyList();
		}
	}

}
