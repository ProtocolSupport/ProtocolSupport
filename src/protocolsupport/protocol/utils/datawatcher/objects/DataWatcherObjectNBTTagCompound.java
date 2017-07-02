package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class DataWatcherObjectNBTTagCompound extends DataWatcherObject<NBTTagCompoundWrapper> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = ItemStackSerializer.readTag(from, version);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		ItemStackSerializer.writeTag(to, version, value);
	}

}
