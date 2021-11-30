package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2_18;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public PlayerListSetEntry(IMiddlePacketInit init) {
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
		io.writeClientbound(playerlistsetentry);
	}

}
