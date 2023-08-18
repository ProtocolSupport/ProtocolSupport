package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSystemMessage;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemMessage extends MiddleSystemMessage implements IClientboundMiddlePacketV7 {

	public SystemMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		if (overlay) {
			return;
		}

		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeVarIntUTF8String(chat, ChatCodec.serialize(version, clientCache.getLocale(), message));
		io.writeClientbound(chat);
	}

}
