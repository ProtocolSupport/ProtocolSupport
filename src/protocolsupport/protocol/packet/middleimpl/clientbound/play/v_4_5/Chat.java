package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData chat = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeShortUTF16BEString(chat, message.toLegacyText(cache.getAttributesCache().getLocale()));
		codec.write(chat);
	}

}
