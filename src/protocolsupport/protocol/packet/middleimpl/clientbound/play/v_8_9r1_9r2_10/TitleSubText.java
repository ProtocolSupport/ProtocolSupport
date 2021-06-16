package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleSubText;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class TitleSubText extends MiddleTitleSubText {

	public TitleSubText(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData titlesubtextPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TITLE_SUBTEXT);
		VarNumberSerializer.writeVarInt(titlesubtextPacket, 1); //legacy title action (1 - set sub text)
		StringSerializer.writeVarIntUTF8String(titlesubtextPacket, ChatSerializer.serialize(version, clientCache.getLocale(), text));
		codec.writeClientbound(titlesubtextPacket);
	}

}
