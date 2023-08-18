package protocolsupport.protocol.codec;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.types.ChatSession;

public class ProfileCodec {

	public static ChatSession readChatSession(ByteBuf from) {
		UUID sessionId = UUIDCodec.readUUID(from);
		long timestamp = from.readLong();
		byte[] key = ArrayCodec.readVarIntByteArray(from);
		byte[] signature = ArrayCodec.readVarIntByteArray(from);
		return new ChatSession(sessionId, timestamp, key, signature);
	}

	public static ProfileProperty readProfileProperty(ByteBuf from) {
		String key = StringCodec.readVarIntUTF8String(from);
		String value = StringCodec.readVarIntUTF8String(from);
		if (!from.readBoolean()) {
			return new ProfileProperty(key, value);
		}
		String signature = StringCodec.readVarIntUTF8String(from);
		return new ProfileProperty(key, value, signature);
	}


	public static void writeChatSession(ByteBuf to, ChatSession chatSession) {
		UUIDCodec.writeUUID(to, chatSession.sessionId());
		to.writeLong(chatSession.expiresAt());
		ArrayCodec.writeVarIntByteArray(to, chatSession.publicKey());
		ArrayCodec.writeVarIntByteArray(to, chatSession.signature());
	}

	public static void writeProfileProperty(ByteBuf to, ProfileProperty property) {
		StringCodec.writeVarIntUTF8String(to, property.getName());
		StringCodec.writeVarIntUTF8String(to, property.getValue());
		to.writeBoolean(property.hasSignature());
		if (property.hasSignature()) {
			StringCodec.writeVarIntUTF8String(to, property.getSignature());
		}
	}

}
