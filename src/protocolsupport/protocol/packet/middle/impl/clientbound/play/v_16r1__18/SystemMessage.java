package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1__18;

import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSystemMessage;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemMessage extends MiddleSystemMessage implements
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public SystemMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String messageJson = ChatCodec.serialize(version, clientCache.getLocale(), message);
		io.writeClientbound(!overlay ? PlayerMessage.createChatMessage(messageJson, PlayerMessage.SENDER_SYSTEM) : PlayerMessage.createActionBar(messageJson, PlayerMessage.SENDER_SYSTEM));
	}

}
