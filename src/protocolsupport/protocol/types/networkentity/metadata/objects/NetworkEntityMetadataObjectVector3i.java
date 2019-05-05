package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVector3i extends NetworkEntityMetadataObject<Position> {

	public NetworkEntityMetadataObjectVector3i(Position pos) {
		this.value = pos;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		PositionSerializer.writeLegacyPositionI(to, value);
	}

}
