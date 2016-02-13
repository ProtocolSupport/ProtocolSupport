package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.util.UUID;

import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage.PlayerListEntry;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.transformer.utils.LegacyUtils;

public abstract class MiddlePlayerInfo<T> extends ClientBoundMiddlePacket<T> {

	protected Action action;
	protected Info[] infos;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		action = Action.values()[serializer.readVarInt()];
		infos = new Info[serializer.readVarInt()];
		for (int i = 0; i < infos.length; i++) {
			Info info = new Info();
			info.uuid = serializer.readUUID();
			switch (action) {
				case ADD: {
					info.username = serializer.readString(16);
					info.properties = new Property[serializer.readVarInt()];
					for (int j = 0; j < info.properties.length; j++) {
						String name = serializer.readString(32767);
						String value = serializer.readString(32767);
						String signature = null;
						if (serializer.readBoolean()) {
							signature = serializer.readString(32767);
						}
						info.properties[j] = new Property(name, value, signature);
					}
					info.gamemode = serializer.readVarInt();
					info.ping = serializer.readVarInt();
					if (serializer.readBoolean()) {
						info.displayNameJson = serializer.readString(Short.MAX_VALUE);
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
						info.displayNameJson = serializer.readString(Short.MAX_VALUE);
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
			info.previousinfo = storage.getPlayerListEntry(info.uuid);
			if (info.previousinfo != null) {
				info.previousinfo = info.previousinfo.clone();
			}
			switch (action) {
				case ADD: {
					PlayerListEntry entry = new PlayerListEntry(info.username);
					entry.setDisplayNameJson(info.displayNameJson);
					for (Property property : info.properties) {
						entry.getProperties().add(property);
					}
					storage.addPlayerListEntry(info.uuid, entry);
					break;
				}
				case DISPLAY_NAME: {
					PlayerListEntry entry = storage.getPlayerListEntry(info.uuid);
					if (entry != null) {
						entry.setDisplayNameJson(info.displayNameJson);
					}
					break;
				}
				case REMOVE: {
					storage.removePlayerListEntry(info.uuid);
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
		public PlayerListEntry previousinfo;
		public String username;
		public int ping;
		public int gamemode;
		public String displayNameJson;
		public Property[] properties;

		public String getName() {
			return displayNameJson == null ? username : LegacyUtils.toText(ChatSerializer.a(displayNameJson));
		}
	}

}
