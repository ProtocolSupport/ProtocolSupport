package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1__18;

import java.util.UUID;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractUnsignedPlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class PlayerMessage extends AbstractUnsignedPlayerMessage implements
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public PlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(createChatMessage(ChatCodec.serialize(version, clientCache.getLocale(), getMessage()), senderId));
	}


	public static final UUID SENDER_SYSTEM = new UUID(0, 0);

	public static ClientBoundPacketData createChatMessage(String messageJson, UUID senderId) {
		ClientBoundPacketData chatPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeVarIntUTF8String(chatPacket, messageJson);
		chatPacket.writeByte(0); //chat type (0 - normal chat)
		UUIDCodec.writeUUID(chatPacket, senderId);
		return chatPacket;
	}

	public static ClientBoundPacketData createActionBar(String messageJson, UUID senderId) {
		ClientBoundPacketData chatPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeVarIntUTF8String(chatPacket, messageJson);
		chatPacket.writeByte(2); //chat type (2 - hotbar)
		UUIDCodec.writeUUID(chatPacket, senderId);
		return chatPacket;
	}

}
