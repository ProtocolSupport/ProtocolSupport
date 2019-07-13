package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVarLong extends NetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectVarLong() {
	}

	public NetworkEntityMetadataObjectVarLong(Integer Value) {
		this.value = Value;
	}

	public NetworkEntityMetadataObjectVarLong(int Value) {
		this.value = Value;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarLong(to, value);
	}

}
