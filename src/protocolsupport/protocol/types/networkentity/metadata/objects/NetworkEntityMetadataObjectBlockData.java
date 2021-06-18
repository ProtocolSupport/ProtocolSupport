package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectBlockData extends ReadableNetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectBlockData(int blockdata) {
		this.value = blockdata;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = VarNumberCodec.readVarInt(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberCodec.writeVarInt(to, value);
	}

}
