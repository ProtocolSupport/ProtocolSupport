package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2_10;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleActionbar;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.Chat;
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
		io.writeClientbound(Chat.create(MessagePosition.HOTBAR, ChatCodec.serialize(version, clientCache.getLocale(), text)));
	}

}
