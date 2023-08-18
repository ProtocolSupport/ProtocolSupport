package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__5;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractUnsignedPlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class PlayerMessage extends AbstractUnsignedPlayerMessage implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5 {

	public PlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(create(getMessage().toLegacyText(clientCache.getLocale())));
	}

	public static ClientBoundPacketData create(String message) {
		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeShortUTF16BEString(chat, message);
		return chat;
	}

}
