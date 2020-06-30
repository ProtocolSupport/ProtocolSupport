package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16;

import java.util.UUID;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeToClient() {
		message = LegacyChatJson.convert(version, clientCache.getLocale(), message);

		codec.write(create(position, message, sender));
	}

	public static ClientBoundPacketData create(MessagePosition position, BaseComponent message, UUID sender) {
		ClientBoundPacketData chat = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeVarIntUTF8String(chat, ChatAPI.toJSON(message));
		MiscSerializer.writeByteEnum(chat, position);
		UUIDSerializer.writeUUID2L(chat, sender);
		return chat;
	}

}
