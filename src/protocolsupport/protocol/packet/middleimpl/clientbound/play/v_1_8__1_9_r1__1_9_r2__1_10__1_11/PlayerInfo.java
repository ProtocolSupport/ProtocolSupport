package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import com.mojang.authlib.properties.Property;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerInfo extends MiddlePlayerInfo<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID, version);
		serializer.writeVarInt(action.ordinal());
		serializer.writeVarInt(infos.length);
		for (Info info : infos) {
			serializer.writeUUID(info.uuid);
			switch (action) {
				case ADD: {
					serializer.writeString(info.username);
					serializer.writeVarInt(info.properties.length);
					for (Property property : info.properties) {
						serializer.writeString(property.getName());
						serializer.writeString(property.getValue());
						serializer.writeBoolean(property.hasSignature());
						if (property.hasSignature()) {
							serializer.writeString(property.getSignature());
						}
					}
					serializer.writeVarInt(info.gamemode);
					serializer.writeVarInt(info.ping);
					serializer.writeBoolean(info.displayNameJson != null);
					if (info.displayNameJson != null) {
						serializer.writeString(info.displayNameJson);
					}
					break;
				}
				case GAMEMODE: {
					serializer.writeVarInt(info.gamemode);
					break;
				}
				case PING: {
					serializer.writeVarInt(info.ping);
					break;
				}
				case DISPLAY_NAME: {
					serializer.writeBoolean(info.displayNameJson != null);
					if (info.displayNameJson != null) {
						serializer.writeString(info.displayNameJson);
					}
					break;
				}
				case REMOVE: {
					break;
				}
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
