package protocolsupport.protocol.pipeline.initial;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ProtocolUtils {

	protected static ProtocolVersion get16PingVersion(int protocolId) {
		switch (protocolId) {
			case 78: {
				return ProtocolVersion.MINECRAFT_1_6_4;
			}
			case 74: {
				return ProtocolVersion.MINECRAFT_1_6_2;
			}
			case 73: {
				return ProtocolVersion.MINECRAFT_1_6_1;
			}
			default: {
				return ProtocolVersion.MINECRAFT_1_6_4;
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected static ProtocolVersion readOldHandshake(ByteBuf data) {
		ProtocolVersion version = ProtocolVersion.fromId(data.readUnsignedByte());
		return version != ProtocolVersion.UNKNOWN ? version : ProtocolVersion.MINECRAFT_LEGACY;
	}

	@SuppressWarnings("deprecation")
	protected static ProtocolVersion readNettyHandshake(ByteBuf data) {
		int packetId = VarNumberSerializer.readVarInt(data);
		if (packetId == 0x00) {
			ProtocolVersion version = ProtocolVersion.fromId(VarNumberSerializer.readVarInt(data));
			return version != ProtocolVersion.UNKNOWN ? version : ProtocolVersion.MINECRAFT_FUTURE;
		} else {
			throw new DecoderException(packetId + " is not a valid packet id");
		}
	}

}
