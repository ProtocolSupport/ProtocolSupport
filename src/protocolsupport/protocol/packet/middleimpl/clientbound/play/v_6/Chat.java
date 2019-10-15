package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6;

import com.google.gson.JsonObject;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
		StringSerializer.writeShortUTF16BEString(serializer, encode(message.toLegacyText(cache.getAttributesCache().getLocale())));
		return RecyclableSingletonList.create(serializer);
	}

	private static String encode(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("text", message);
		return Utils.GSON.toJson(jsonObject);
	}

}
