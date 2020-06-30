package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class Chat extends MiddleChat {

	protected final ClientCache clientCache = cache.getClientCache();

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		if (position == MessagePosition.HOTBAR) {
			return;
		}

		ClientBoundPacketData chat = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeVarIntUTF8String(chat, ChatAPI.toJSON(LegacyChatJson.convert(version, clientCache.getLocale(), message)));
		codec.write(chat);
	}

}
