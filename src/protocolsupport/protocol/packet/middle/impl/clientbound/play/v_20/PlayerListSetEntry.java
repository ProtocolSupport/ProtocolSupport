package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.ProfileCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;
import protocolsupport.utils.BitUtils;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry implements
IClientboundMiddlePacketV20 {

	public PlayerListSetEntry(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData playerlistsetentryPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_LIST_ENTRY_UPDATE);
		playerlistsetentryPacket.writeByte(actions);
		ArrayCodec.writeVarIntTArray(playerlistsetentryPacket, entries, (entryData, entry) -> {
			UUIDCodec.writeUUID(entryData, entry.getUUID());
			PlayerListEntryData currentEntry = entry.getNewData();
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_ADD)) {
				StringCodec.writeVarIntUTF8String(entryData, currentEntry.getUserName());
				ArrayCodec.writeVarIntTArray(entryData, currentEntry.getProperties(), ProfileCodec::writeProfileProperty);
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_INIT_CHAT)) {
				OptionalCodec.writeOptional(playerlistsetentryPacket, currentEntry.getChatSession(), ProfileCodec::writeChatSession);
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_GAMEMODE)) {
				VarNumberCodec.writeVarInt(entryData, currentEntry.getGameMode().getId());
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY)) {
				entryData.writeBoolean(currentEntry.getDisplay());
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_PING)) {
				VarNumberCodec.writeVarInt(entryData, currentEntry.getPing());
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY_NAME)) {
				OptionalCodec.writeOptional(entryData, currentEntry.getDisplayNameJson(), StringCodec::writeVarIntUTF8String);
			}
		});
		io.writeClientbound(playerlistsetentryPacket);
	}

}
