package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Chat extends MiddleChat implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5
{

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
		StringCodec.writeShortUTF16BEString(chat, message.toLegacyText(clientCache.getLocale()));
		io.writeClientbound(chat);
	}

}
