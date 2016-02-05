package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.properties.Property;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage.PlayerListEntry;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleSpawnNamed<T> extends ClientBoundMiddlePacket<T> {

	protected int playerEntityId;
	protected UUID uuid;
	protected String name;
	protected int x;
	protected int y;
	protected int z;
	protected int yaw;
	protected int pitch;
	protected int itemId;
	protected List<Property> properties;
	protected WatchedEntity wplayer;
	protected TIntObjectMap<DataWatcherObject> metadata;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		playerEntityId = serializer.readVarInt();
		uuid = serializer.g();
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		yaw = serializer.readUnsignedByte();
		pitch = serializer.readUnsignedByte();
		itemId = serializer.readUnsignedShort();
		metadata = DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, ChannelUtils.toArray(serializer));
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
