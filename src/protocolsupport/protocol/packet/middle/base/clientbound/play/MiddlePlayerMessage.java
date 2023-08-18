package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.BytesCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddlePlayerMessage extends ClientBoundMiddlePacket {

	protected MiddlePlayerMessage(IMiddlePacketInit init) {
		super(init);
	}

	protected UUID senderId;
	protected int index;
	protected byte[] signature;
	protected String message;
	protected long timestamp;
	protected long salt;
	protected PreviousMessage[] previousMessages;
	protected BaseComponent unsignedMessage;
	protected ChatFilterType filterType;
	protected long[] filterBits;
	protected int chatType;
	protected BaseComponent senderDisplayName;
	protected BaseComponent targetDisplayName;

	@Override
	protected void decode(ByteBuf serverdata) {
		senderId = UUIDCodec.readUUID(serverdata);
		index = VarNumberCodec.readVarInt(serverdata);
		signature = OptionalCodec.readOptional(serverdata, signatureFrom -> BytesCodec.readBytes(signatureFrom, 256));
		message = StringCodec.readVarIntUTF8String(serverdata);
		timestamp = serverdata.readLong();
		salt = serverdata.readLong();
		previousMessages = ArrayCodec.readVarIntTArray(serverdata, PreviousMessage.class, previousMessageData -> {
			int prevIndex = VarNumberCodec.readVarInt(serverdata);
			byte[] signature = null;
			if (prevIndex == -1) {
				signature = BytesCodec.readBytes(previousMessageData, 256);
			}
			return new PreviousMessage(prevIndex, signature);
		});
		unsignedMessage = OptionalCodec.readOptional(serverdata, unsignedMessageData -> ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(unsignedMessageData), true));
		filterType = MiscDataCodec.readVarIntEnum(serverdata, ChatFilterType.CONSTANT_LOOKUP);
		if (filterType == ChatFilterType.PARTIALLY_FILTERED) {
			filterBits = ArrayCodec.readVarIntLongArray(serverdata);
		}
		chatType = VarNumberCodec.readVarInt(serverdata);
		senderDisplayName = ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(serverdata), true);
		targetDisplayName = OptionalCodec.readOptional(serverdata, unsignedMessageData -> ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(unsignedMessageData), true));
	}

	protected static record PreviousMessage(int index, byte[] signature) {
	}

	protected static enum ChatFilterType {
		PASS_THROUGH, FULLY_FILTERED, PARTIALLY_FILTERED;
		public static final EnumConstantLookup<ChatFilterType> CONSTANT_LOOKUP = new EnumConstantLookup<>(ChatFilterType.class);
	}

}
