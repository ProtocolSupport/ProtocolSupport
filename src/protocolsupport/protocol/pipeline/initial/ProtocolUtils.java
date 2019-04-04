package protocolsupport.protocol.pipeline.initial;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

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

	protected static ProtocolVersion readOldHandshake(ByteBuf data) {
		//<1.3.1 sends username first, and protocol version only after that
		//the username is limited to 16 symbols, and string length is a bing endian short
		//so it the first byte is not 0 - it's a >= 1.3.1 handshake, if 0, it's < 1.3.1 one
		//in case of < 1.3.1 versions we have no knowledge of which version was that until later
		//so we just return one of the old versions for now, and switch to another one later
		int versionByte = data.readUnsignedByte();
		if (versionByte != 0) {
			return ProtocolVersionsHelper.getOldProtocolVersion(versionByte);
		} else {
			return ProtocolVersion.MINECRAFT_BETA_1_7_3;
		}
	}

	protected static ProtocolVersion readNewHandshake(ByteBuf data) {
		int packetId = VarNumberSerializer.readVarInt(data);
		if (packetId == 0x00) {
			return ProtocolVersionsHelper.getNewProtocolVersion(VarNumberSerializer.readVarInt(data));
		} else {
			throw new DecoderException(packetId + " is not a valid packet id");
		}
	}

}
