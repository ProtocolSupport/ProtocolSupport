package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1__18;

import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractUnsignedSystemPlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemPlayerMessage extends AbstractUnsignedSystemPlayerMessage implements
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public SystemPlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(PlayerMessage.createChatMessage(ChatCodec.serialize(version, clientCache.getLocale(), getMessage()), PlayerMessage.SENDER_SYSTEM));
	}

}
