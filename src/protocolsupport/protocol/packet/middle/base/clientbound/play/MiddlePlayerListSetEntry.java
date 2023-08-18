package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.ArrayList;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ProfileCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.PlayerListCache;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;
import protocolsupport.protocol.types.ChatSession;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.utils.BitUtils;

public abstract class MiddlePlayerListSetEntry extends ClientBoundMiddlePacket {

	protected MiddlePlayerListSetEntry(IMiddlePacketInit init) {
		super(init);
	}

	protected static final int FLAGS_BIT_ADD = 0;
	protected static final int FLAGS_BIT_INIT_CHAT = 1;
	protected static final int FLAGS_BIT_GAMEMODE = 2;
	protected static final int FLAGS_BIT_DISPLAY = 3;
	protected static final int FLAGS_BIT_PING = 4;
	protected static final int FLAGS_BIT_DISPLAY_NAME = 5;

	protected final PlayerListCache playerlistCache = cache.getPlayerListCache();

	protected byte actions;
	protected final ArrayList<PlayerListEntry> entries = new ArrayList<>();

	@Override
	protected void decode(ByteBuf serverdata) {
		actions = serverdata.readByte();
		int entryCount = VarNumberCodec.readVarInt(serverdata);
		while (entryCount-- > 0) {
			UUID uuid = UUIDCodec.readUUID(serverdata);
			String username = null;
			ProfileProperty[] properties = null;
			ChatSession chatdata = null;
			GameMode gamemode = null;
			Boolean display = null;
			Integer ping = null;
			String displayNameJson = null;
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_ADD)) {
				username = StringCodec.readVarIntUTF8String(serverdata);
				properties = ArrayCodec.readVarIntTArray(serverdata, ProfileProperty.class, ProfileCodec::readProfileProperty);
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_INIT_CHAT)) {
				if (serverdata.readBoolean()) {
					chatdata = ProfileCodec.readChatSession(serverdata);
				}
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_GAMEMODE)) {
				gamemode = GameMode.getById(VarNumberCodec.readVarInt(serverdata));
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY)) {
				display = serverdata.readBoolean();
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_PING)) {
				ping = VarNumberCodec.readVarInt(serverdata);
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY_NAME)) {
				if (serverdata.readBoolean()) {
					displayNameJson = StringCodec.readVarIntUTF8String(serverdata);
				}
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_ADD)) {
				PlayerListEntryData currentData = new PlayerListEntryData(username, properties, display, chatdata, ping, gamemode, displayNameJson);
				PlayerListEntryData oldData = playerlistCache.add(uuid, currentData);
				if (oldData != null) {
					oldData = oldData.clone();
				}
				entries.add(new PlayerListEntry(uuid, oldData, currentData));
			} else {
				PlayerListEntryData currentData = playerlistCache.get(uuid);
				if (currentData != null) {
					PlayerListEntryData oldData = currentData.clone();
					if (BitUtils.isIBitSet(actions, FLAGS_BIT_INIT_CHAT)) {
						currentData.setChatData(chatdata);
					}
					if (BitUtils.isIBitSet(actions, FLAGS_BIT_GAMEMODE)) {
						currentData.setGameMode(gamemode);
					}
					if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY)) {
						currentData.setDisplay(display);
					}
					if (BitUtils.isIBitSet(actions, FLAGS_BIT_PING)) {
						currentData.setPing(ping);
					}
					if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY_NAME)) {
						currentData.setDisplayNameJson(displayNameJson);
					}
					entries.add(new PlayerListEntry(uuid, oldData, currentData));
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

}
