package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractUnsignedSystemPlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemPlayerMessage extends AbstractUnsignedSystemPlayerMessage implements IClientboundMiddlePacketV7 {

	public SystemPlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeVarIntUTF8String(chat, ChatCodec.serialize(version, clientCache.getLocale(), getMessage()));
		io.writeClientbound(chat);
	}

}
