package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerMessage;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.utils.JavaSystemProperty;

public class PlayerMessage extends MiddlePlayerMessage implements IClientboundMiddlePacketV20 {

	protected static final boolean chatUnsigned = JavaSystemProperty.getValue("chat.unsigned", false, Boolean::parseBoolean);

	public PlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		if (chatUnsigned) {
			io.writeClientbound(SystemPlayerMessage.create(
				version, clientCache.getLocale(),
				unsignedMessage != null ? unsignedMessage : new TextComponent(message), chatType, senderDisplayName, targetDisplayName
			));
			return;
		}

		ClientBoundPacketData playermessagePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_MESSAGE);
		UUIDCodec.writeUUID(playermessagePacket, senderId);
		VarNumberCodec.writeVarInt(playermessagePacket, index);
		OptionalCodec.writeOptional(playermessagePacket, signature, ByteBuf::writeBytes);
		StringCodec.writeVarIntUTF8String(playermessagePacket, message);
		playermessagePacket.writeLong(timestamp);
		playermessagePacket.writeLong(salt);
		ArrayCodec.writeVarIntTArray(playermessagePacket, previousMessages, (previousMessageData, previousMessageElement) -> {
			VarNumberCodec.writeVarInt(previousMessageData, previousMessageElement.index());
			if (previousMessageElement.index() == -1) {
				previousMessageData.writeBytes(previousMessageElement.signature());
			}
		});
		OptionalCodec.writeOptional(playermessagePacket, ChatCodec.serialize(version, clientCache.getLocale(), unsignedMessage), StringCodec::writeVarIntUTF8String);
		MiscDataCodec.writeVarIntEnum(playermessagePacket, filterType);
		if (filterType == ChatFilterType.PARTIALLY_FILTERED) {
			ArrayCodec.writeVarIntLongArray(playermessagePacket, filterBits);
		}
		VarNumberCodec.writeVarInt(playermessagePacket, chatType);
		StringCodec.writeVarIntUTF8String(playermessagePacket, ChatCodec.serialize(version, clientCache.getLocale(), senderDisplayName));
		OptionalCodec.writeOptional(playermessagePacket, ChatCodec.serialize(version, clientCache.getLocale(), targetDisplayName), StringCodec::writeVarIntUTF8String);
		io.writeClientbound(playermessagePacket);
	}

}
