package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import java.util.Map.Entry;
import java.util.UUID;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData playerlistsetentry = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_INFO);
		VarNumberSerializer.writeVarInt(playerlistsetentry, action.ordinal());
		VarNumberSerializer.writeVarInt(playerlistsetentry, infos.size());
		for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
			UUIDSerializer.writeUUID2L(playerlistsetentry, entry.getKey());
			PlayerListEntry currentEntry = entry.getValue().getNewEntry();
			switch (action) {
				case ADD: {
					StringSerializer.writeVarIntUTF8String(playerlistsetentry, currentEntry.getUserName());
					ArraySerializer.writeVarIntTArray(playerlistsetentry, currentEntry.getProperties(), (to, property) -> {
						StringSerializer.writeVarIntUTF8String(to, property.getName());
						StringSerializer.writeVarIntUTF8String(to, property.getValue());
						to.writeBoolean(property.hasSignature());
						if (property.hasSignature()) {
							StringSerializer.writeVarIntUTF8String(to, property.getSignature());
						}
					});
					VarNumberSerializer.writeVarInt(playerlistsetentry, currentEntry.getGameMode().getId());
					VarNumberSerializer.writeVarInt(playerlistsetentry, currentEntry.getPing());
					String displayNameJson = currentEntry.getDisplayNameJson();
					playerlistsetentry.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringSerializer.writeVarIntUTF8String(playerlistsetentry, displayNameJson);
					}
					break;
				}
				case GAMEMODE: {
					VarNumberSerializer.writeVarInt(playerlistsetentry, currentEntry.getGameMode().getId());
					break;
				}
				case PING: {
					VarNumberSerializer.writeVarInt(playerlistsetentry, currentEntry.getPing());
					break;
				}
				case DISPLAY_NAME: {
					String displayNameJson = currentEntry.getDisplayNameJson();
					playerlistsetentry.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringSerializer.writeVarIntUTF8String(playerlistsetentry, displayNameJson);
					}
					break;
				}
				case REMOVE: {
					break;
				}
			}
		}
		codec.writeClientbound(playerlistsetentry);
	}

}
