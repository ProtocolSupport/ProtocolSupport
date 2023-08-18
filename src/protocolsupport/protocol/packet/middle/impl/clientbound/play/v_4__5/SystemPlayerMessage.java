package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__5;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractUnsignedSystemPlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemPlayerMessage extends AbstractUnsignedSystemPlayerMessage implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5 {

	public SystemPlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(PlayerMessage.create(getMessage().toLegacyText(clientCache.getLocale())));
	}

}
