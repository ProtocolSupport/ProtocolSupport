package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectItemStack extends ReadableNetworkEntityMetadataObject<NetworkItemStack> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = ItemStackSerializer.readItemStack(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		ItemStackSerializer.writeItemStack(to, version, locale, value);
	}

}
