package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;

public abstract class MiddlePlayerInfo<T> extends ClientBoundMiddlePacket<T> {

	protected Action action;
	protected Info[] infos;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		action = Action.values()[serializer.readVarInt()];
		infos = new Info[serializer.readVarInt()];
		for (int i = 0; i < infos.length; i++) {
			Info info = new Info();
			info.uuid = serializer.readUUID();
			switch (action) {
				case ADD: {
					info.username = serializer.readString(16);
					info.properties = new ProfileProperty[serializer.readVarInt()];
					for (int j = 0; j < info.properties.length; j++) {
						String name = serializer.readString();
						String value = serializer.readString();
						String signature = null;
						if (serializer.readBoolean()) {
							signature = serializer.readString();
						}
						info.properties[j] = new ProfileProperty(name, value, signature);
					}
					info.gamemode = serializer.readVarInt();
					info.ping = serializer.readVarInt();
					if (serializer.readBoolean()) {
						info.displayNameJson = serializer.readString();
					}
					break;
				}
				case GAMEMODE: {
					info.gamemode = serializer.readVarInt();
					break;
				}
				case PING: {
					info.ping = serializer.readVarInt();
					break;
				}
				case DISPLAY_NAME: {
					if (serializer.readBoolean()) {
						info.displayNameJson = serializer.readString();
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
