package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleActionbar;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.Chat;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Actionbar extends MiddleActionbar {

	public Actionbar(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		codec.writeClientbound(Chat.create(MessagePosition.HOTBAR, ChatSerializer.serialize(version, clientCache.getLocale(), text)));
	}

}
