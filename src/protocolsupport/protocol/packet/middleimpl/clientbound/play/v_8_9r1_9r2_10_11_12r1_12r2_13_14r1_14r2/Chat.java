package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData serializer = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeVarIntUTF8String(serializer, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), message)));
		MiscSerializer.writeByteEnum(serializer, position);
		codec.write(serializer);
	}

}
