package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectItemStack extends NetworkEntityMetadataObject<NetworkItemStack> {

	public NetworkEntityMetadataObjectItemStack(NetworkItemStack itemstack) {
		this.value = itemstack;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		ItemStackCodec.writeItemStack(to, version, value);
	}

}
