package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import java.util.Map.Entry;
import java.util.UUID;

import protocolsupport.api.utils.Any;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
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
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_PLAYER_INFO);
		VarNumberSerializer.writeVarInt(serializer, action.ordinal());
		VarNumberSerializer.writeVarInt(serializer, infos.size());
		for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
			MiscSerializer.writeUUID(serializer, entry.getKey());
			PlayerListEntry currentEntry = entry.getValue().getObj2();
			switch (action) {
				case ADD: {
					StringSerializer.writeVarIntUTF8String(serializer, currentEntry.getUserName());
					ArraySerializer.writeVarIntTArray(serializer, currentEntry.getProperties(false), (to, property) -> {
						StringSerializer.writeVarIntUTF8String(serializer, property.getName());
						StringSerializer.writeVarIntUTF8String(serializer, property.getValue());
						serializer.writeBoolean(property.hasSignature());
						if (property.hasSignature()) {
							StringSerializer.writeVarIntUTF8String(serializer, property.getSignature());
						}
					});
					VarNumberSerializer.writeVarInt(serializer, currentEntry.getGameMode().getId());
					VarNumberSerializer.writeVarInt(serializer, currentEntry.getPing());
					String displayNameJson = currentEntry.getDisplayNameJson();
					serializer.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringSerializer.writeVarIntUTF8String(serializer, displayNameJson);
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
						StringSerializer.writeVarIntUTF8String(serializer, displayNameJson);
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
