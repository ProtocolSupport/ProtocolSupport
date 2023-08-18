package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSystemPlayerMessage;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class SystemPlayerMessage extends MiddleSystemPlayerMessage
implements IClientboundMiddlePacketV20 {

	public SystemPlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(create(version, clientCache.getLocale(), message, chatType, senderDisplayName, targetDisplayName));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, BaseComponent message, int chatType, BaseComponent senderDisplayName, BaseComponent targetDisplayName) {
		ClientBoundPacketData systemplayermessagePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SYSTEM_PLAYER_MESSAGE);
		StringCodec.writeVarIntUTF8String(systemplayermessagePacket, ChatCodec.serialize(version, locale, message));
		VarNumberCodec.writeVarInt(systemplayermessagePacket, chatType);
		StringCodec.writeVarIntUTF8String(systemplayermessagePacket, ChatCodec.serialize(version, locale, senderDisplayName));
		OptionalCodec.writeOptionalVarIntUTF8String(systemplayermessagePacket, ChatCodec.serialize(version, locale, targetDisplayName));
		return systemplayermessagePacket;
	}

}
