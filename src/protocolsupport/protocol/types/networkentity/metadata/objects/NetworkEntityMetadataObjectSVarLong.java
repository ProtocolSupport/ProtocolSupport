package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectSVarLong extends NetworkEntityMetadataObject<Long> {

	public NetworkEntityMetadataObjectSVarLong() {
	}

	public NetworkEntityMetadataObjectSVarLong(long value) {
		this.value = value;
	}

	public NetworkEntityMetadataObjectSVarLong(int value) {
		this.value = (long) value;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeSVarLong(to, value);
	}

}
