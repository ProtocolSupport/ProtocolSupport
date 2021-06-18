package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.types.BlockDirection;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectDirection extends NetworkEntityMetadataObject<BlockDirection> {

	public NetworkEntityMetadataObjectDirection(BlockDirection direction) {
		this.value = direction;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		MiscDataCodec.writeVarIntEnum(to, value);
	}

}
