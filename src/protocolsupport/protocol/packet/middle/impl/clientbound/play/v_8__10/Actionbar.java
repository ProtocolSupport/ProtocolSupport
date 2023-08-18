package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__10;

import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleActionbar;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__15.PlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Actionbar extends MiddleActionbar implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10 {

	public Actionbar(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(PlayerMessage.createActionBar(ChatCodec.serialize(version, clientCache.getLocale(), text)));
	}

}
