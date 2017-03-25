package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class DataWatcherObjectItemStack extends DataWatcherObject<ItemStackWrapper> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = ItemStackSerializer.readItemStack(from, version);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		ItemStackSerializer.writeItemStack(to, version, value);
	}

}
