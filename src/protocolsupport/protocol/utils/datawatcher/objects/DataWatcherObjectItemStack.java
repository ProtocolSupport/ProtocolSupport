package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkItemStack;

public class DataWatcherObjectItemStack extends ReadableDataWatcherObject<NetworkItemStack> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = ItemStackSerializer.readItemStack(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		ItemStackSerializer.writeItemStack(to, version, locale, value);
	}

}
