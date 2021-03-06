package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalVarInt extends NetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectOptionalVarInt() {
	}

	public NetworkEntityMetadataObjectOptionalVarInt(int i) {
		this.value = i;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberCodec.writeVarInt(to, value != null ? value + 1 : 0);
	}

}
