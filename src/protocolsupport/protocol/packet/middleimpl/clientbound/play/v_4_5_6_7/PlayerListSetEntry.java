package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntryData;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String locale = cache.getClientCache().getLocale();
		switch (action) {
			case ADD: {
				for (PlayerListEntry entry : entries) {
					PlayerListEntryData oldData = entry.getOldData();
					if (oldData != null) {
						codec.writeClientbound(createRemove(version, oldData.getCurrentName(locale)));
					}
					PlayerListEntryData currentData = entry.getNewData();
					codec.writeClientbound(createAddOrUpdate(version, currentData.getCurrentName(locale), (short) currentData.getPing()));
				}
				break;
			}
			case PING: {
				for (PlayerListEntry entry : entries) {
					PlayerListEntryData currentData = entry.getNewData();
					codec.writeClientbound(createAddOrUpdate(version, currentData.getCurrentName(locale), (short) currentData.getPing()));
				}
				break;
			}
			case DISPLAY_NAME: {
				for (PlayerListEntry entry : entries) {
					codec.writeClientbound(createRemove(version, entry.getOldData().getCurrentName(locale)));
					PlayerListEntryData currentData = entry.getNewData();
					codec.writeClientbound(createAddOrUpdate(version, currentData.getCurrentName(locale), (short) currentData.getPing()));
				}
				break;
			}
			case REMOVE: {
				for (PlayerListEntry entry : entries) {
					codec.writeClientbound(createRemove(version, entry.getOldData().getCurrentName(locale)));
				}
				break;
			}
			default: {
				break;
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
		ClientBoundPacketData playerinfo = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_INFO);
		StringCodec.writeString(playerinfo, version, LegacyChat.clampLegacyText(name, 16));
		playerinfo.writeBoolean(addOrUpdate);
		playerinfo.writeShort(ping);
		return playerinfo;
	}

}
