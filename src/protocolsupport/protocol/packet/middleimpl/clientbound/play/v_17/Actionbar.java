package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleActionbar;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Actionbar extends MiddleActionbar {

	public Actionbar(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		ClientBoundPacketData actionbarPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_ACTIONBAR);
		StringSerializer.writeVarIntUTF8String(actionbarPacket, ChatSerializer.serialize(version, clientCache.getLocale(), text));
		codec.writeClientbound(actionbarPacket);
	}

}
