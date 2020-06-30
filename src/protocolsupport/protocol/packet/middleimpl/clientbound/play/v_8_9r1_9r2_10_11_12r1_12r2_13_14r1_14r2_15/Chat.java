package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
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
		message = LegacyChatJson.convert(version, clientCache.getLocale(), message);

		codec.write(create(position, message));
	}

	public static ClientBoundPacketData create(MessagePosition position, BaseComponent message) {
		ClientBoundPacketData chat = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeVarIntUTF8String(chat, ChatAPI.toJSON(message));
		MiscSerializer.writeByteEnum(chat, position);
		return chat;
	}

}
