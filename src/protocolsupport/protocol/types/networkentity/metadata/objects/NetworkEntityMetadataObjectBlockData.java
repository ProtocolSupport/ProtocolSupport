package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectBlockData extends NetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectBlockData(int blockdata) {
		this.value = blockdata;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberCodec.writeVarInt(to, value);
	}

}
