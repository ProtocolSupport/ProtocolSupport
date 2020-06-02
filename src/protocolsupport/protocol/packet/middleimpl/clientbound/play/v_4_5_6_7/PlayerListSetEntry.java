package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Map.Entry;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		String locale = cache.getAttributesCache().getLocale();
		switch (action) {
			case ADD: {
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					PlayerListEntry oldEntry = entry.getValue().getOldEntry();
					if (oldEntry != null) {
						codec.write(createRemove(codec, version, oldEntry.getCurrentName(locale)));
					}
					PlayerListEntry currentEntry = entry.getValue().getNewEntry();
					codec.write(createAddOrUpdate(codec, version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case PING: {
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					PlayerListEntry currentEntry = entry.getValue().getNewEntry();
					codec.write(createAddOrUpdate(codec, version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case DISPLAY_NAME: {
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					codec.write(createRemove(codec, version, entry.getValue().getOldEntry().getCurrentName(locale)));
					PlayerListEntry currentEntry = entry.getValue().getNewEntry();
					codec.write(createAddOrUpdate(codec, version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case REMOVE: {
				for (Entry<UUID, PlayerListOldNewEntry> entry : infos.entrySet()) {
					codec.write(createRemove(codec, version, entry.getValue().getOldEntry().getCurrentName(locale)));
				}
				break;
			}
			default: {
				break;
			}
		}
	}

	protected static ClientBoundPacketData createAddOrUpdate(PacketDataCodec codec ,ProtocolVersion version, String name, short ping) {
		return create(codec, version, name, true, ping);
	}

	protected static ClientBoundPacketData createRemove(PacketDataCodec codec, ProtocolVersion version, String name) {
		return create(codec, version, name, false, (short) 0);
	}

	protected static ClientBoundPacketData create(PacketDataCodec codec, ProtocolVersion version, String name, boolean addOrUpdate, short ping) {
		ClientBoundPacketData playerinfo = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_PLAYER_INFO);
		StringSerializer.writeString(playerinfo, version, LegacyChat.clampLegacyText(name, 16));
		playerinfo.writeBoolean(addOrUpdate);
		playerinfo.writeShort(ping);
		return playerinfo;
	}

}
