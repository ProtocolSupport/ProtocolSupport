package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.Any;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.utils.Utils;

public abstract class MiddlePlayerListSetEntry extends ClientBoundMiddlePacket {

	public MiddlePlayerListSetEntry(ConnectionImpl connection) {
		super(connection);
	}

	protected Action action;
	protected final HashMap<UUID, Any<PlayerListEntry, PlayerListEntry>> infos = new HashMap<>();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		infos.clear();
		PlayerListCache plcache = cache.getPlayerListCache();
		action = MiscSerializer.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		Utils.repeat(VarNumberSerializer.readVarInt(serverdata), () -> {
			UUID uuid = MiscSerializer.readUUID(serverdata);
			switch (action) {
				case ADD: {
					PlayerListEntry oldEntry = plcache.getEntry(uuid);
					if (oldEntry != null) {
						oldEntry = oldEntry.clone();
					}
					String username = StringSerializer.readVarIntUTF8String(serverdata);
					ArrayList<ProfileProperty> properties = new ArrayList<>();
					Utils.repeat(VarNumberSerializer.readVarInt(serverdata), () -> {
						String name = StringSerializer.readVarIntUTF8String(serverdata);
						String value = StringSerializer.readVarIntUTF8String(serverdata);
						String signature = null;
						if (serverdata.readBoolean()) {
							signature = StringSerializer.readVarIntUTF8String(serverdata);
						}
						properties.add(new ProfileProperty(name, value, signature));
					});
					GameMode gamemode = GameMode.getById(VarNumberSerializer.readVarInt(serverdata));
					int ping = VarNumberSerializer.readVarInt(serverdata);
					String displayNameJson = null;
					if (serverdata.readBoolean()) {
						displayNameJson = StringSerializer.readVarIntUTF8String(serverdata);
					}
					PlayerListEntry currentEntry = new PlayerListEntry(username, ping, gamemode, displayNameJson, properties);
					plcache.addEntry(uuid, currentEntry);
					infos.put(uuid, new Any<>(oldEntry, currentEntry));
					break;
				}
				case GAMEMODE: {
					GameMode gamemode = GameMode.getById(VarNumberSerializer.readVarInt(serverdata));
					PlayerListEntry currentEntry = plcache.getEntry(uuid);
					if (currentEntry != null) {
						PlayerListEntry oldEntry = currentEntry.clone();
						currentEntry.setGameMode(gamemode);
						infos.put(uuid, new Any<>(oldEntry, currentEntry));
					}
					break;
				}
				case PING: {
					int ping = VarNumberSerializer.readVarInt(serverdata);
					PlayerListEntry currentEntry = plcache.getEntry(uuid);
					if (currentEntry != null) {
						PlayerListEntry oldEntry = currentEntry.clone();
						currentEntry.setPing(ping);
						infos.put(uuid, new Any<>(oldEntry, currentEntry));
					}
					break;
				}
				case DISPLAY_NAME: {
					String displayNameJson = null;
					if (serverdata.readBoolean()) {
						displayNameJson = StringSerializer.readVarIntUTF8String(serverdata);
					}
					PlayerListEntry currentEntry = plcache.getEntry(uuid);
					if (currentEntry != null) {
						PlayerListEntry oldEntry = currentEntry.clone();
						currentEntry.setDisplayNameJson(displayNameJson);
						infos.put(uuid, new Any<>(oldEntry, currentEntry));
					}
					break;
				}
				case REMOVE: {
					PlayerListEntry oldEntry = plcache.removeEntry(uuid);
					if (oldEntry != null) {
						infos.put(uuid, new Any<>(oldEntry, null));
					}
					break;
				}
			}
		});
	}

	@Override
	public boolean postFromServerRead() {
		return !infos.isEmpty();
	}

	protected static enum Action {
		ADD, GAMEMODE, PING, DISPLAY_NAME, REMOVE;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
