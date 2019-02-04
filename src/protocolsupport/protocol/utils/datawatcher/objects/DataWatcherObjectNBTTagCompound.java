package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;

public class DataWatcherObjectNBTTagCompound extends ReadableDataWatcherObject<NBTCompound> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = ItemStackSerializer.readTag(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		ItemStackSerializer.writeTag(to, version, value);
	}

}
