package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.utils.BitUtils;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public PlayerListSetEntry(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (BitUtils.isIBitSet(actions, FLAGS_BIT_ADD)) {
			for (PlayerListEntry entry : entries) {
				PlayerListEntryData oldData = entry.getOldData();
				if (oldData != null) {
					io.writeClientbound(createRemove(version, oldData.getLegacyDisplayName()));
				}
				PlayerListEntryData currentData = entry.getNewData();
				io.writeClientbound(createAddOrUpdate(version, currentData.getLegacyDisplayName(), (short) currentData.getPing()));
			}
		} else {
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_PING)) {
				for (PlayerListEntry entry : entries) {
					PlayerListEntryData currentData = entry.getNewData();
					io.writeClientbound(createAddOrUpdate(version, currentData.getLegacyDisplayName(), (short) currentData.getPing()));
				}
			}
			if (BitUtils.isIBitSet(actions, FLAGS_BIT_DISPLAY_NAME)) {
				for (PlayerListEntry entry : entries) {
					io.writeClientbound(createRemove(version, entry.getOldData().getLegacyDisplayName()));
					PlayerListEntryData currentData = entry.getNewData();
					io.writeClientbound(createAddOrUpdate(version, currentData.getLegacyDisplayName(), (short) currentData.getPing()));
				}
			}
		}
	}

	protected static ClientBoundPacketData createAddOrUpdate(ProtocolVersion version, String name, short ping) {
		return create(version, name, true, ping);
	}

	protected static ClientBoundPacketData createRemove(ProtocolVersion version, String name) {
		return create(version, name, false, (short) 0);
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String name, boolean addOrUpdate, short ping) {
		ClientBoundPacketData playerinfo = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_LIST_ENTRY_UPDATE);
		StringCodec.writeString(playerinfo, version, LegacyChat.clampLegacyText(name, 16));
		playerinfo.writeBoolean(addOrUpdate);
		playerinfo.writeShort(ping);
		return playerinfo;
	}

}
