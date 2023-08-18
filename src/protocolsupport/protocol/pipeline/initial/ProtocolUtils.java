package protocolsupport.protocol.pipeline.initial;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ProtocolUtils {

	private ProtocolUtils() {
	}

	protected static ProtocolVersion get16PingVersion(int protocolId) {
		return switch (protocolId) {
			case 78 -> ProtocolVersion.MINECRAFT_1_6_4;
			case 74 -> ProtocolVersion.MINECRAFT_1_6_2;
			case 73 -> ProtocolVersion.MINECRAFT_1_6_1;
			default -> ProtocolVersion.MINECRAFT_1_6_4;
		};
	}

	protected static ProtocolVersion readOldHandshake(ByteBuf data) {
		//pre 1.3.1 doesn't send a protocol version
		//but we assume first byte as protocol versions anyway because the chance of string length first byte being a valid protocol versions number is too small
		return ProtocolVersionsHelper.getOldProtocolVersion(data.readUnsignedByte());
	}

	protected static ProtocolVersion readNewHandshake(ByteBuf data) {
		int packetId = VarNumberCodec.readVarInt(data);
		if (packetId == 0x00) {
			return ProtocolVersionsHelper.getNewProtocolVersion(VarNumberCodec.readVarInt(data));
		} else {
			throw new DecoderException(packetId + " is not a valid packet id");
		}
	}

}
