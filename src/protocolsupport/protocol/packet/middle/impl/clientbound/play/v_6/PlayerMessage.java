package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6;

import com.google.gson.JsonObject;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractUnsignedPlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.utils.JsonUtils;

public class PlayerMessage extends AbstractUnsignedPlayerMessage implements IClientboundMiddlePacketV6 {

	public PlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(create(getMessage().toLegacyText(clientCache.getLocale())));
	}

	public static ClientBoundPacketData create(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("text", message);

		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeShortUTF16BEString(chat, JsonUtils.GSON.toJson(jsonObject));
		return chat;
	}

}
