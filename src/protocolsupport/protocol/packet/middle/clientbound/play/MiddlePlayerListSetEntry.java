package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.PlayerListCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddlePlayerListSetEntry extends ClientBoundMiddlePacket {

	protected MiddlePlayerListSetEntry(MiddlePacketInit init) {
		super(init);
	}

	protected final PlayerListCache playerlistCache = cache.getPlayerListCache();

	protected Action action;
	protected List<PlayerListEntry> entries = new ArrayList<>();

	@Override
	protected void decode(ByteBuf serverdata) {
		action = MiscDataCodec.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		int entryCount = VarNumberCodec.readVarInt(serverdata);
		while (entryCount-- > 0) {
			UUID uuid = UUIDCodec.readUUID2L(serverdata);
			switch (action) {
				case ADD: {
					String username = StringCodec.readVarIntUTF8String(serverdata);
					ArrayList<ProfileProperty> properties = new ArrayList<>();
					int propertiesCount = VarNumberCodec.readVarInt(serverdata);
					while (propertiesCount-- > 0) {
						String name = StringCodec.readVarIntUTF8String(serverdata);
						String value = StringCodec.readVarIntUTF8String(serverdata);
						String signature = null;
						if (serverdata.readBoolean()) {
							signature = StringCodec.readVarIntUTF8String(serverdata);
						}
						properties.add(new ProfileProperty(name, value, signature));
					}
					GameMode gamemode = GameMode.getById(VarNumberCodec.readVarInt(serverdata));
					int ping = VarNumberCodec.readVarInt(serverdata);
					String displayNameJson = null;
					if (serverdata.readBoolean()) {
						displayNameJson = StringCodec.readVarIntUTF8String(serverdata);
					}
					PlayerListEntryData currentData = new PlayerListEntryData(username, ping, gamemode, displayNameJson, properties);
					PlayerListEntryData oldData = playerlistCache.add(uuid, currentData);
					if (oldData != null) {
						oldData = oldData.clone();
					}
					entries.add(new PlayerListEntry(uuid, oldData, currentData));
					break;
				}
				case GAMEMODE: {
					GameMode gamemode = GameMode.getById(VarNumberCodec.readVarInt(serverdata));
					PlayerListEntryData currentData = playerlistCache.get(uuid);
					if (currentData != null) {
						PlayerListEntryData oldData = currentData.clone();
						currentData.setGameMode(gamemode);
						entries.add(new PlayerListEntry(uuid, oldData, currentData));
					}
					break;
				}
				case PING: {
					int ping = VarNumberCodec.readVarInt(serverdata);
					PlayerListEntryData currentData = playerlistCache.get(uuid);
					if (currentData != null) {
						PlayerListEntryData oldData = currentData.clone();
						currentData.setPing(ping);
						entries.add(new PlayerListEntry(uuid, oldData, currentData));
					}
					break;
				}
				case DISPLAY_NAME: {
					String displayNameJson = null;
					if (serverdata.readBoolean()) {
						displayNameJson = StringCodec.readVarIntUTF8String(serverdata);
					}
					PlayerListEntryData currentData = playerlistCache.get(uuid);
					if (currentData != null) {
						PlayerListEntryData oldData = currentData.clone();
						currentData.setDisplayNameJson(displayNameJson);
						entries.add(new PlayerListEntry(uuid, oldData, currentData));
					}
					break;
				}
				case REMOVE: {
					PlayerListEntryData oldData = playerlistCache.remove(uuid);
					if (oldData != null) {
						entries.add(new PlayerListEntry(uuid, oldData, null));
					}
					break;
				}
			}
		}
	}

	@Override
	protected void cleanup() {
		entries.clear();
	}

	protected static class PlayerListEntry {

		protected final UUID uuid;
		protected final PlayerListEntryData oldEntry;
		protected final PlayerListEntryData newEntry;

		public PlayerListEntry(UUID uuid, PlayerListEntryData oldEntry, PlayerListEntryData newEntry) {
			this.uuid = uuid;
			this.oldEntry = oldEntry;
			this.newEntry = newEntry;
		}

		public UUID getUUID() {
			return uuid;
		}

		public PlayerListEntryData getOldData() {
			return oldEntry;
		}

		public PlayerListEntryData getNewData() {
			return newEntry;
		}

	}

	protected enum Action {
		ADD, GAMEMODE, PING, DISPLAY_NAME, REMOVE;
		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
