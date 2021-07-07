package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData playerlistsetentry = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_INFO);
		VarNumberCodec.writeVarInt(playerlistsetentry, action.ordinal());
		VarNumberCodec.writeVarInt(playerlistsetentry, entries.size());
		for (PlayerListEntry entry : entries) {
			UUIDCodec.writeUUID2L(playerlistsetentry, entry.getUUID());
			PlayerListEntryData currentData = entry.getNewData();
			switch (action) {
				case ADD: {
					StringCodec.writeVarIntUTF8String(playerlistsetentry, currentData.getUserName());
					ArrayCodec.writeVarIntTArray(playerlistsetentry, currentData.getProperties(), (to, property) -> {
						StringCodec.writeVarIntUTF8String(to, property.getName());
						StringCodec.writeVarIntUTF8String(to, property.getValue());
						to.writeBoolean(property.hasSignature());
						if (property.hasSignature()) {
							StringCodec.writeVarIntUTF8String(to, property.getSignature());
						}
					});
					VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getGameMode().getId());
					VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getPing());
					String displayNameJson = currentData.getDisplayNameJson();
					playerlistsetentry.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringCodec.writeVarIntUTF8String(playerlistsetentry, displayNameJson);
					}
					break;
				}
				case GAMEMODE: {
					VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getGameMode().getId());
					break;
				}
				case PING: {
					VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getPing());
					break;
				}
				case DISPLAY_NAME: {
					String displayNameJson = currentData.getDisplayNameJson();
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
