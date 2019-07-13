package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectSVarInt extends NetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectSVarInt() {
	}

	public NetworkEntityMetadataObjectSVarInt(int value) {
		this.value = value;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeSVarInt(to, value);
	}

}
