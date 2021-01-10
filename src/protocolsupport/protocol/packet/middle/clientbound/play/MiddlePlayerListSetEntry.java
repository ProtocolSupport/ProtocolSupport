package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddlePlayerListSetEntry extends ClientBoundMiddlePacket {

	protected final PlayerListCache playerlistCache = cache.getPlayerListCache();

	public MiddlePlayerListSetEntry(MiddlePacketInit init) {
		super(init);
	}

	protected Action action;
	protected final HashMap<UUID, PlayerListOldNewEntry> infos = new HashMap<>();

	@Override
	protected void decode(ByteBuf serverdata) {
		action = MiscSerializer.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		int entryCount = VarNumberSerializer.readVarInt(serverdata);
		while (entryCount-- > 0) {
			UUID uuid = UUIDSerializer.readUUID2L(serverdata);
			switch (action) {
				case ADD: {
					PlayerListEntry oldEntry = playerlistCache.getEntry(uuid);
					if (oldEntry != null) {
						oldEntry = oldEntry.clone();
					}
					String username = StringSerializer.readVarIntUTF8String(serverdata);
					ArrayList<ProfileProperty> properties = new ArrayList<>();
					int propertiesCount = VarNumberSerializer.readVarInt(serverdata);
					while (propertiesCount-- > 0) {
						String name = StringSerializer.readVarIntUTF8String(serverdata);
						String value = StringSerializer.readVarIntUTF8String(serverdata);
						String signature = null;
						if (serverdata.readBoolean()) {
							signature = StringSerializer.readVarIntUTF8String(serverdata);
						}
						properties.add(new ProfileProperty(name, value, signature));
					};
					GameMode gamemode = GameMode.getById(VarNumberSerializer.readVarInt(serverdata));
					int ping = VarNumberSerializer.readVarInt(serverdata);
					String displayNameJson = null;
					if (serverdata.readBoolean()) {
						displayNameJson = StringSerializer.readVarIntUTF8String(serverdata);
					}
					PlayerListEntry currentEntry = new PlayerListEntry(username, ping, gamemode, displayNameJson, properties);
					playerlistCache.addEntry(uuid, currentEntry);
					infos.put(uuid, new PlayerListOldNewEntry(oldEntry, currentEntry));
					break;
				}
				case GAMEMODE: {
					GameMode gamemode = GameMode.getById(VarNumberSerializer.readVarInt(serverdata));
					PlayerListEntry currentEntry = playerlistCache.getEntry(uuid);
					if (currentEntry != null) {
						PlayerListEntry oldEntry = currentEntry.clone();
						currentEntry.setGameMode(gamemode);
						infos.put(uuid, new PlayerListOldNewEntry(oldEntry, currentEntry));
					}
					break;
				}
				case PING: {
					int ping = VarNumberSerializer.readVarInt(serverdata);
					PlayerListEntry currentEntry = playerlistCache.getEntry(uuid);
					if (currentEntry != null) {
						PlayerListEntry oldEntry = currentEntry.clone();
						currentEntry.setPing(ping);
						infos.put(uuid, new PlayerListOldNewEntry(oldEntry, currentEntry));
					}
					break;
				}
				case DISPLAY_NAME: {
					String displayNameJson = null;
					if (serverdata.readBoolean()) {
						displayNameJson = StringSerializer.readVarIntUTF8String(serverdata);
					}
					PlayerListEntry currentEntry = playerlistCache.getEntry(uuid);
					if (currentEntry != null) {
						PlayerListEntry oldEntry = currentEntry.clone();
						currentEntry.setDisplayNameJson(displayNameJson);
						infos.put(uuid, new PlayerListOldNewEntry(oldEntry, currentEntry));
					}
					break;
				}
				case REMOVE: {
					PlayerListEntry oldEntry = playerlistCache.removeEntry(uuid);
					if (oldEntry != null) {
						infos.put(uuid, new PlayerListOldNewEntry(oldEntry, null));
					}
					break;
				}
			}
		};
	}

	@Override
	protected void cleanup() {
		infos.clear();
	}

	protected static class PlayerListOldNewEntry {

		protected final PlayerListEntry oldEntry;
		protected final PlayerListEntry newEntry;

		public PlayerListOldNewEntry(PlayerListEntry oldEntry, PlayerListEntry newEntry) {
			this.oldEntry = oldEntry;
			this.newEntry = newEntry;
		}

		public PlayerListEntry getOldEntry() {
			return oldEntry;
		}

		public PlayerListEntry getNewEntry() {
			return newEntry;
		}

	}

	protected static enum Action {
		ADD, GAMEMODE, PING, DISPLAY_NAME, REMOVE;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
