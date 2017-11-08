package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerInfo extends MiddlePlayerInfo {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID, version);
		VarNumberSerializer.writeVarInt(serializer, action.ordinal());
		VarNumberSerializer.writeVarInt(serializer, infos.length);
		for (Info info : infos) {
			MiscSerializer.writeUUID(serializer, connection.getVersion(), info.uuid);
			switch (action) {
				case ADD: {
					StringSerializer.writeString(serializer, version, info.username);
					VarNumberSerializer.writeVarInt(serializer, info.properties.length);
					for (ProfileProperty property : info.properties) {
						StringSerializer.writeString(serializer, version, property.getName());
						StringSerializer.writeString(serializer, version, property.getValue());
						serializer.writeBoolean(property.hasSignature());
						if (property.hasSignature()) {
							StringSerializer.writeString(serializer, version, property.getSignature());
						}
					}
					VarNumberSerializer.writeVarInt(serializer, info.gamemode.getId());
					VarNumberSerializer.writeVarInt(serializer, info.ping);
					serializer.writeBoolean(info.displayNameJson != null);
					if (info.displayNameJson != null) {
						StringSerializer.writeString(serializer, version, info.displayNameJson);
					}
					break;
				}
				case GAMEMODE: {
					VarNumberSerializer.writeVarInt(serializer, info.gamemode.getId());
					break;
				}
				case PING: {
					VarNumberSerializer.writeVarInt(serializer, info.ping);
					break;
				}
				case DISPLAY_NAME: {
					serializer.writeBoolean(info.displayNameJson != null);
					if (info.displayNameJson != null) {
						StringSerializer.writeString(serializer, version, info.displayNameJson);
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
