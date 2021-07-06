package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1_16r2_17r1_17r2;

import java.util.UUID;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Chat extends MiddleChat {

	public Chat(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		codec.writeClientbound(create(position, ChatCodec.serialize(version, clientCache.getLocale(), message), sender));
	}

	public static ClientBoundPacketData create(MessagePosition position, String messageJson, UUID sender) {
		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHAT);
		StringCodec.writeVarIntUTF8String(chat, messageJson);
		MiscDataCodec.writeByteEnum(chat, position);
		UUIDCodec.writeUUID2L(chat, sender);
		return chat;
	}

}
