package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectPosition extends NetworkEntityMetadataObject<Position> {

	public NetworkEntityMetadataObjectPosition(Position position) {
		this.value = position;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		writePositionL(to, version, value);
	}

	protected static void writePositionL(ByteBuf to, ProtocolVersion version, Position position) {
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14)) {
			PositionCodec.writePosition(to, position);
		} else {
			PositionCodec.writePositionLXYZ(to, position);
		}
	}

}
