package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import java.util.Map.Entry;
import java.util.UUID;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData playerlistsetentry = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_INFO);
		VarNumberCodec.writeVarInt(playerlistsetentry, action.ordinal());
		VarNumberCodec.writeVarInt(playerlistsetentry, infos.size());
		for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
			UUIDCodec.writeUUID2L(playerlistsetentry, entry.getKey());
			PlayerListEntry currentEntry = entry.getValue().getNewEntry();
			switch (action) {
				case ADD: {
					StringCodec.writeVarIntUTF8String(playerlistsetentry, currentEntry.getUserName());
					ArrayCodec.writeVarIntTArray(playerlistsetentry, currentEntry.getProperties(), (to, property) -> {
						StringCodec.writeVarIntUTF8String(to, property.getName());
						StringCodec.writeVarIntUTF8String(to, property.getValue());
						to.writeBoolean(property.hasSignature());
						if (property.hasSignature()) {
							StringCodec.writeVarIntUTF8String(to, property.getSignature());
						}
					});
					VarNumberCodec.writeVarInt(playerlistsetentry, currentEntry.getGameMode().getId());
					VarNumberCodec.writeVarInt(playerlistsetentry, currentEntry.getPing());
					String displayNameJson = currentEntry.getDisplayNameJson();
					playerlistsetentry.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringCodec.writeVarIntUTF8String(playerlistsetentry, displayNameJson);
					}
					break;
				}
				case GAMEMODE: {
					VarNumberCodec.writeVarInt(playerlistsetentry, currentEntry.getGameMode().getId());
					break;
				}
				case PING: {
					VarNumberCodec.writeVarInt(playerlistsetentry, currentEntry.getPing());
					break;
				}
				case DISPLAY_NAME: {
					String displayNameJson = currentEntry.getDisplayNameJson();
					playerlistsetentry.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringCodec.writeVarIntUTF8String(playerlistsetentry, displayNameJson);
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
