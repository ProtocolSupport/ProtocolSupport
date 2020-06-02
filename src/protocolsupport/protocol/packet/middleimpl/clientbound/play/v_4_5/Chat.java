package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.AttributesCache;

public class Chat extends MiddleChat {

	protected final AttributesCache clientCache = cache.getAttributesCache();

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		if (position == MessagePosition.HOTBAR) {
			return;
		}

		ClientBoundPacketData chat = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeShortUTF16BEString(chat, message.toLegacyText(clientCache.getLocale()));
		codec.write(chat);
	}

}
