package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;

public abstract class MiddlePlayerInfo extends ClientBoundMiddlePacket {

	protected Action action;
	protected Info[] infos;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		action = Action.values()[VarNumberSerializer.readVarInt(serverdata)];
		infos = new Info[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < infos.length; i++) {
			Info info = new Info();
			info.uuid = MiscSerializer.readUUID(serverdata);
			switch (action) {
				case ADD: {
					info.username = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
					info.properties = new ProfileProperty[VarNumberSerializer.readVarInt(serverdata)];
					for (int j = 0; j < info.properties.length; j++) {
						String name = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
						String value = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
						String signature = null;
						if (serverdata.readBoolean()) {
							signature = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
						}
						info.properties[j] = new ProfileProperty(name, value, signature);
					}
					info.gamemode = VarNumberSerializer.readVarInt(serverdata);
					info.ping = VarNumberSerializer.readVarInt(serverdata);
					if (serverdata.readBoolean()) {
						info.displayNameJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
					}
					break;
				}
				case GAMEMODE: {
					info.gamemode = VarNumberSerializer.readVarInt(serverdata);
					break;
				}
				case PING: {
					info.ping = VarNumberSerializer.readVarInt(serverdata);
					break;
				}
				case DISPLAY_NAME: {
					if (serverdata.readBoolean()) {
						info.displayNameJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
					}
					break;
				}
				case REMOVE: {
					break;
				}
			}
			infos[i] = info;
		}
	}

	@Override
	public void handle() {
		for (Info info : infos) {
			info.previousinfo = cache.getPlayerListEntry(info.uuid);
			if (info.previousinfo != null) {
				info.previousinfo = info.previousinfo.clone();
			}
			switch (action) {
				case ADD: {
					NetworkDataCache.PlayerListEntry entry = new NetworkDataCache.PlayerListEntry(info.username);
					entry.setDisplayNameJson(info.displayNameJson);
					for (ProfileProperty property : info.properties) {
						entry.getProperties().add(property);
					}
					cache.addPlayerListEntry(info.uuid, entry);
					break;
				}
				case DISPLAY_NAME: {
					NetworkDataCache.PlayerListEntry entry = cache.getPlayerListEntry(info.uuid);
					if (entry != null) {
						entry.setDisplayNameJson(info.displayNameJson);
					}
					break;
				}
				case REMOVE: {
					cache.removePlayerListEntry(info.uuid);
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	protected static enum Action {
		ADD, GAMEMODE, PING, DISPLAY_NAME, REMOVE
	}

	protected static class Info {
		public UUID uuid;
		public NetworkDataCache.PlayerListEntry previousinfo;
		public String username;
		public int ping;
		public int gamemode;
		public String displayNameJson;
		public ProfileProperty[] properties;

		public String getName() {
			return displayNameJson == null ? username : ChatAPI.fromJSON(displayNameJson).toLegacyText();
		}
	}

}
