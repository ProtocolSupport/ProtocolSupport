package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__15;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__18.AbstractUnsignedPlayerMessage;
import protocolsupport.protocol.storage.netcache.ClientCache;

public class PlayerMessage extends AbstractUnsignedPlayerMessage implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15 {

	protected final ClientCache clientCache = cache.getClientCache();

	public PlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(createChatMessage(ChatCodec.serialize(version, clientCache.getLocale(), getMessage())));
	}

	public static ClientBoundPacketData createChatMessage(String messageJson) {
		ClientBoundPacketData chatPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeVarIntUTF8String(chatPacket, messageJson);
		chatPacket.writeByte(0); //chat type (0 - normal chat)
		return chatPacket;
	}

	public static ClientBoundPacketData createActionBar(String messageJson) {
		ClientBoundPacketData chatPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		StringCodec.writeVarIntUTF8String(chatPacket, messageJson);
		chatPacket.writeByte(2); //chat type (2 - hotbar)
		return chatPacket;
	}

}
