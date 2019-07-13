package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectNBTTagCompound extends ReadableNetworkEntityMetadataObject<NBTCompound> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = ItemStackSerializer.readTag(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		ItemStackSerializer.writeTag(to, version, value);
	}

}
