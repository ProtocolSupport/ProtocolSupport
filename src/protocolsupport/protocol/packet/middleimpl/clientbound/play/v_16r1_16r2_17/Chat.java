package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1_16r2_17;

import java.util.UUID;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Chat extends MiddleChat {

	public Chat(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		codec.writeClientbound(create(position, ChatSerializer.serialize(version, clientCache.getLocale(), message), sender));
	}

	public static ClientBoundPacketData create(MessagePosition position, String messageJson, UUID sender) {
		ClientBoundPacketData chat = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeVarIntUTF8String(chat, messageJson);
		MiscSerializer.writeByteEnum(chat, position);
		UUIDSerializer.writeUUID2L(chat, sender);
		return chat;
	}

}
