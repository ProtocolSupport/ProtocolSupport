package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Map.Entry;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
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
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					PlayerListEntry oldEntry = entry.getValue().getOldEntry();
					if (oldEntry != null) {
						codec.writeClientbound(createRemove(version, oldEntry.getCurrentName(locale)));
					}
					PlayerListEntry currentEntry = entry.getValue().getNewEntry();
					codec.writeClientbound(createAddOrUpdate(version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case PING: {
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					PlayerListEntry currentEntry = entry.getValue().getNewEntry();
					codec.writeClientbound(createAddOrUpdate(version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case DISPLAY_NAME: {
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					codec.writeClientbound(createRemove(version, entry.getValue().getOldEntry().getCurrentName(locale)));
					PlayerListEntry currentEntry = entry.getValue().getNewEntry();
					codec.writeClientbound(createAddOrUpdate(version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case REMOVE: {
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					codec.writeClientbound(createRemove(version, entry.getValue().getOldEntry().getCurrentName(locale)));
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
