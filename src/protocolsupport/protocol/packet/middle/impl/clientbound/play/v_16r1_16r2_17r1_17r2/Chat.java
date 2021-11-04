package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1_16r2_17r1_17r2;

import java.util.UUID;

import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class Chat extends MiddleChat implements
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public Chat(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(create(position, ChatCodec.serialize(version, clientCache.getLocale(), message), sender));
	}

	public static ClientBoundPacketData create(MessagePosition position, String messageJson, UUID sender) {
		ClientBoundPacketData chat = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHAT);
		StringCodec.writeVarIntUTF8String(chat, messageJson);
		MiscDataCodec.writeByteEnum(chat, position);
		UUIDCodec.writeUUID2L(chat, sender);
		return chat;
	}

}
