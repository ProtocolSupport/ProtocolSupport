package protocolsupport.protocol.pipeline.initial;

import java.util.Arrays;

import gnu.trove.map.hash.TIntObjectHashMap;
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

	protected static ProtocolVersion readOldHandshake(ByteBuf data) {
		return getOldProtocolVersion(data.readUnsignedByte());
	}

	protected static ProtocolVersion readNewHandshake(ByteBuf data) {
		int packetId = VarNumberSerializer.readVarInt(data);
		if (packetId == 0x00) {
			return getNewProtocolVersion(VarNumberSerializer.readVarInt(data));
		} else {
			throw new DecoderException(packetId + " is not a valid packet id");
		}
	}

	private static final TIntObjectHashMap<ProtocolVersion> byOldProtocolId = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<ProtocolVersion> byNewProtocolId = new TIntObjectHashMap<>();
	static {
		Arrays.stream(ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_6_4)).forEach(version -> byOldProtocolId.put(version.getId(), version));
		Arrays.stream(ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_7_5)).forEach(version -> byNewProtocolId.put(version.getId(), version));
	}

	protected static ProtocolVersion getOldProtocolVersion(int protocolid) {
		ProtocolVersion version = byOldProtocolId.get(protocolid);
		return version != null ? version : ProtocolVersion.MINECRAFT_LEGACY;
	}

	protected static ProtocolVersion getNewProtocolVersion(int protocolid) {
		ProtocolVersion version = byNewProtocolId.get(protocolid);
		return version != null ? version : ProtocolVersion.MINECRAFT_FUTURE;
	}

}
