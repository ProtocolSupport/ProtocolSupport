package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleText;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class TitleText extends MiddleTitleText {

	public TitleText(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData titletextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_TEXT);
		VarNumberCodec.writeVarInt(titletextPacket, 0); //legacy title action (0 - set main text)
		StringCodec.writeVarIntUTF8String(titletextPacket, ChatCodec.serialize(version, clientCache.getLocale(), text));
		codec.writeClientbound(titletextPacket);
	}

}
