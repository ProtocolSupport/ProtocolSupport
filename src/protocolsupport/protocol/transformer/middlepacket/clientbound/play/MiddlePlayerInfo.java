package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.util.UUID;

import com.mojang.authlib.properties.Property;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

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
						info.properties[j] = signature != null ? new Property(name, value, signature) : new Property(value, name);
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
	public void handle(LocalStorage storage) {
		for (Info info : infos) {
			if (action == Action.ADD) {
				storage.addPlayerListName(info.uuid, info.username);
				for (Property property : info.properties) {
					storage.addPropertyData(info.uuid, property);
				}
			} else {
				info.username = storage.getPlayerListName(info.uuid);
			}
		}
	}

	protected static enum Action {
		ADD, GAMEMODE, PING, DISPLAY_NAME, REMOVE
	}

	protected static class Info {
		public UUID uuid;
		public String username;
		public Property[] properties;
		public int gamemode;
		public int ping;
		public String displayNameJson;
	}

}
