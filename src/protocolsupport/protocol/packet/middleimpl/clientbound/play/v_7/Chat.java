package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Chat extends MiddleChat {

	protected final ClientCache clientCache = cache.getClientCache();

	public Chat(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (position == MessagePosition.HOTBAR) {
			return;
		}

		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHAT);
		StringSerializer.writeVarIntUTF8String(chat, ChatSerializer.serialize(version, clientCache.getLocale(), message));
		codec.writeClientbound(chat);
	}

}
