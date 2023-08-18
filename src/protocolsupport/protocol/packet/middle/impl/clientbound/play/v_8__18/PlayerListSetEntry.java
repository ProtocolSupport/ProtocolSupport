package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__18;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ProfileCodec;
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
import protocolsupport.utils.BitUtils;

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
		ClientBoundPacketData playerlistsetentry = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_LIST_ENTRY_UPDATE);
		if (BitUtils.isIBitSet(actions, FLAGS_BIT_ADD)) {
			VarNumberCodec.writeVarInt(playerlistsetentry, 0); //action, 0 - add
			VarNumberCodec.writeVarInt(playerlistsetentry, entries.size());
			for (PlayerListEntry entry : entries) {
				PlayerListEntryData currentData = entry.getNewData();
				UUIDCodec.writeUUID(playerlistsetentry, entry.getUUID());
				StringCodec.writeVarIntUTF8String(playerlistsetentry, currentData.getUserName());
				ArrayCodec.writeVarIntTArray(playerlistsetentry, currentData.getProperties(), ProfileCodec::writeProfileProperty);
				VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getGameMode().getId());
				VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getPing());
				String displayNameJson = currentData.getDisplayNameJson();
				playerlistsetentry.writeBoolean(displayNameJson != null);
				if (displayNameJson != null) {
					StringCodec.writeVarIntUTF8String(playerlistsetentry, displayNameJson);
				}
			}
			io.writeClientbound(playerlistsetentry);
		} else {
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_GAMEMODE)) {
				VarNumberCodec.writeVarInt(playerlistsetentry, 1);  //action, 1 - gamemode
				VarNumberCodec.writeVarInt(playerlistsetentry, entries.size());
				for (PlayerListEntry entry : entries) {
					PlayerListEntryData currentData = entry.getNewData();
					UUIDCodec.writeUUID(playerlistsetentry, entry.getUUID());
					VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getGameMode().getId());
				}
				io.writeClientbound(playerlistsetentry);
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_PING)) {
				VarNumberCodec.writeVarInt(playerlistsetentry, 2);  //action, 2 - ping
				VarNumberCodec.writeVarInt(playerlistsetentry, entries.size());
				for (PlayerListEntry entry : entries) {
					PlayerListEntryData currentData = entry.getNewData();
					UUIDCodec.writeUUID(playerlistsetentry, entry.getUUID());
					VarNumberCodec.writeVarInt(playerlistsetentry, currentData.getPing());
				}
				io.writeClientbound(playerlistsetentry);
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY_NAME)) {
				VarNumberCodec.writeVarInt(playerlistsetentry, 3);  //action, 3 - display name
				VarNumberCodec.writeVarInt(playerlistsetentry, entries.size());
				for (PlayerListEntry entry : entries) {
					PlayerListEntryData currentData = entry.getNewData();
					String displayNameJson = currentData.getDisplayNameJson();
					UUIDCodec.writeUUID(playerlistsetentry, entry.getUUID());
					playerlistsetentry.writeBoolean(displayNameJson != null);
					if (displayNameJson != null) {
						StringCodec.writeVarIntUTF8String(playerlistsetentry, displayNameJson);
					}
				}
				io.writeClientbound(playerlistsetentry);
			}
		}
	}

}
