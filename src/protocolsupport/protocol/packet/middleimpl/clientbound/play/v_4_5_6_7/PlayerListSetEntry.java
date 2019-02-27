package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Map.Entry;
import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PlayerListSetEntry extends MiddlePlayerListSetEntry {

	public PlayerListSetEntry(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		String locale = cache.getAttributesCache().getLocale();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		switch (action) {
			case ADD: {
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					PlayerListEntry oldEntry = entry.getValue().getObj1();
					PlayerListEntry currentEntry = entry.getValue().getObj2();
					if (oldEntry != null) {
						packets.add(createRemove(version, oldEntry.getCurrentName(locale)));
					}
					packets.add(createAddOrUpdate(version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case PING: {
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					PlayerListEntry currentEntry = entry.getValue().getObj2();
					packets.add(createAddOrUpdate(version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case DISPLAY_NAME: {
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					PlayerListEntry oldEntry = entry.getValue().getObj1();
					PlayerListEntry currentEntry = entry.getValue().getObj2();
					packets.add(createRemove(version, oldEntry.getCurrentName(locale)));
					packets.add(createAddOrUpdate(version, currentEntry.getCurrentName(locale), (short) currentEntry.getPing()));
				}
				break;
			}
			case REMOVE: {
				for (Entry<UUID, Any<PlayerListEntry, PlayerListEntry>> entry : infos.entrySet()) {
					PlayerListEntry oldEntry = entry.getValue().getObj1();
					packets.add(createRemove(version, oldEntry.getCurrentName(locale)));
				}
				break;
			}
			default: {
				break;
			}
		}
		return packets;
	}

	protected static ClientBoundPacketData createAddOrUpdate(ProtocolVersion version, String name, short ping) {
		return create(version, name, true, ping);
	}

	protected static ClientBoundPacketData createRemove(ProtocolVersion version, String name) {
		return create(version, name, false, (short) 0);
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String name, boolean addOrUpdate, short ping) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_PLAYER_INFO_ID);
		StringSerializer.writeString(serializer, version, LegacyChat.clampLegacyText(name, 16));
		serializer.writeBoolean(addOrUpdate);
		serializer.writeShort(ping);
		return serializer;
	}

}
