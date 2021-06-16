package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleSubText;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class TitleSubText extends MiddleTitleSubText {

	public TitleSubText(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData titlesubtextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_SUBTEXT);
		StringSerializer.writeVarIntUTF8String(titlesubtextPacket, ChatSerializer.serialize(version, clientCache.getLocale(), text));
		codec.writeClientbound(titlesubtextPacket);
	}

}
