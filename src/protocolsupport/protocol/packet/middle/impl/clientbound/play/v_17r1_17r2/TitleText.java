package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTitleText;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class TitleText extends MiddleTitleText implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public TitleText(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData titletextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_TEXT);
		StringCodec.writeVarIntUTF8String(titletextPacket, ChatCodec.serialize(version, clientCache.getLocale(), text));
		io.writeClientbound(titletextPacket);
	}

}
