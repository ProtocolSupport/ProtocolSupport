package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSystemMessage;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemMessage extends MiddleSystemMessage implements IClientboundMiddlePacketV6 {

	public SystemMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		if (overlay) {
			return;
		}

		io.writeClientbound(PlayerMessage.create(message.toLegacyText(clientCache.getLocale())));
	}

}
