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
		return ProtocolVersionsHelper.getOldProtocolVersion(data.readUnsignedByte());
	}

	protected static ProtocolVersion readNewHandshake(ByteBuf data) {
		int packetId = VarNumberSerializer.readVarInt(data);
		if (packetId == 0x00) {
			return ProtocolVersionsHelper.getNewProtocolVersion(VarNumberSerializer.readVarInt(data));
		} else {
			throw new DecoderException(packetId + " is not a valid packet id");
		}
	}

	protected static ProtocolVersion readPEHandshake(ByteBuf data) {
		data.readByte();
		data.readByte();
		int incomingversion = data.readInt();
		int cversion = ProtocolVersion.MINECRAFT_PE.getId();
		if (incomingversion == cversion) {
			return ProtocolVersion.MINECRAFT_PE;
		} else if (incomingversion < cversion) {
			return ProtocolVersion.MINECRAFT_PE_LEGACY;
		} else {
			return ProtocolVersion.MINECRAFT_PE_FUTURE;
		}
	}

}
