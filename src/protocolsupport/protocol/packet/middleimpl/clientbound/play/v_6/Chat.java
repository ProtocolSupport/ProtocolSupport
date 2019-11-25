package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6;

import com.google.gson.JsonObject;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData chat = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeShortUTF16BEString(chat, encode(message.toLegacyText(cache.getAttributesCache().getLocale())));
		codec.write(chat);
	}

	protected static String encode(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("text", message);
		return Utils.GSON.toJson(jsonObject);
	}

}
