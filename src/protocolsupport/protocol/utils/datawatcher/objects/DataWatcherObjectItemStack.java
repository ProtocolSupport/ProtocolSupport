package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class DataWatcherObjectItemStack extends DataWatcherObject<ItemStackWrapper> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = ItemStackSerializer.readItemStack(from, version, locale, false);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		ItemStackSerializer.writeItemStack(to, version, locale, value, true);
	}

}
