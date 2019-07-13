package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import protocolsupport.api.utils.Any;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID);
		VarNumberSerializer.writeVarInt(serializer, action.ordinal());
		VarNumberSerializer.writeVarInt(serializer, infos.size());
		for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
			MiscSerializer.writeUUID(serializer, entry.getKey());
			PlayerListEntry currentEntry = entry.getValue().getObj2();
			switch (action) {
				case ADD: {
					StringSerializer.writeString(serializer, version, currentEntry.getUserName());
					List<ProfileProperty> properties = currentEntry.getProperties(false);
					VarNumberSerializer.writeVarInt(serializer, properties.size());
					for (ProfileProperty property : properties) {
						StringSerializer.writeString(serializer, version, property.getName());
						StringSerializer.writeString(serializer, version, property.getValue());
						serializer.writeBoolean(property.hasSignature());
						if (property.hasSignature()) {
							StringSerializer.writeString(serializer, version, property.getSignature());
						}
					}
					VarNumberSerializer.writeVarInt(serializer, currentEntry.getGameMode().getId());
					VarNumberSerializer.writeVarInt(serializer, currentEntry.getPing());
					String displayNameJson = currentEntry.getDisplayNameJson();
					serializer.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringSerializer.writeString(serializer, version, displayNameJson);
					}
					break;
				}
				case GAMEMODE: {
					VarNumberSerializer.writeVarInt(serializer, currentEntry.getGameMode().getId());
					break;
				}
				case PING: {
					VarNumberSerializer.writeVarInt(serializer, currentEntry.getPing());
					break;
				}
				case DISPLAY_NAME: {
					String displayNameJson = currentEntry.getDisplayNameJson();
					serializer.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringSerializer.writeString(serializer, version, displayNameJson);
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
