package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Chat extends MiddleChat implements IClientboundMiddlePacketV7 {

	protected final ClientCache clientCache = cache.getClientCache();

	public Chat(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (position == MessagePosition.HOTBAR) {
			return;
		}

		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHAT);
		StringCodec.writeVarIntUTF8String(chat, ChatCodec.serialize(version, clientCache.getLocale(), message));
		io.writeClientbound(chat);
	}

}
