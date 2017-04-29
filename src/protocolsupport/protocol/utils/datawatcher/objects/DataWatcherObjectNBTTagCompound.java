package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class DataWatcherObjectNBTTagCompound extends DataWatcherObject<NBTTagCompoundWrapper> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) throws DecoderException {
		value = ItemStackSerializer.readTag(from, version);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		ItemStackSerializer.writeTag(to, version, value);
	}

}
