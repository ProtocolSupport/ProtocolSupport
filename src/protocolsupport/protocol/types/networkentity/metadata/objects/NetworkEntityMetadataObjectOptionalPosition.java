package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalPosition extends NetworkEntityMetadataObject<Position> {

	public NetworkEntityMetadataObjectOptionalPosition() {
	}

	public NetworkEntityMetadataObjectOptionalPosition(Position position) {
		this.value = position;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value != null);
		if (value != null) {
			NetworkEntityMetadataObjectPosition.writePositionL(to, version, value);
		}
	}

}
