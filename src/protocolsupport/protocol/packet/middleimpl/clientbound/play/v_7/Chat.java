package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData chat = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeVarIntUTF8String(chat, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), message)));
		codec.write(chat);
	}

}
