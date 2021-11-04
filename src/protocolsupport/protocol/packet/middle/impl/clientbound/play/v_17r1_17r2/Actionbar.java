package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleActionbar;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Actionbar extends MiddleActionbar implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public Actionbar(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData actionbarPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ACTIONBAR);
		StringCodec.writeVarIntUTF8String(actionbarPacket, ChatCodec.serialize(version, clientCache.getLocale(), text));
		io.writeClientbound(actionbarPacket);
	}

}
