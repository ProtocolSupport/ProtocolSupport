package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSystemMessage;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemMessage extends MiddleSystemMessage implements
IClientboundMiddlePacketV20 {

	public SystemMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(create(version, clientCache.getLocale(), message, overlay));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, BaseComponent message, boolean overlay) {
		ClientBoundPacketData systemmessagePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SYSTEM_MESSAGE);
		StringCodec.writeVarIntUTF8String(systemmessagePacket, ChatCodec.serialize(version, locale, message));
		systemmessagePacket.writeBoolean(overlay);
		return systemmessagePacket;
	}

}
