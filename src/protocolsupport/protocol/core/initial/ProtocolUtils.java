package protocolsupport.protocol.core.initial;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.netty.ChannelUtils;

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
		if (ChannelUtils.readVarInt(data) == 0x00) {
			ProtocolVersion version = ProtocolVersion.fromId(ChannelUtils.readVarInt(data));
			return version != ProtocolVersion.UNKNOWN ? version : ProtocolVersion.MINECRAFT_FUTURE;
		}
		return null;
	}

}
