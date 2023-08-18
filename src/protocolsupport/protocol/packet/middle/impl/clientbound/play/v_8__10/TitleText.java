package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__10;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTitleText;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class TitleText extends MiddleTitleText implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10 {

	public TitleText(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData titletextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_TEXT);
		VarNumberCodec.writeVarInt(titletextPacket, 0); //legacy title action (0 - set main text)
		StringCodec.writeVarIntUTF8String(titletextPacket, ChatCodec.serialize(version, clientCache.getLocale(), text));
		io.writeClientbound(titletextPacket);
	}

}
