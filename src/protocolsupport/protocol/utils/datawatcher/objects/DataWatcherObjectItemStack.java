package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class DataWatcherObjectItemStack extends DataWatcherObject<ItemStackWrapper> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		return 5;
	}

	@Override
	public void readFromStream(ProtocolSupportPacketDataSerializer serializer) {
		value = serializer.readItemStack();
	}

	@Override
	public void writeToStream(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeItemStack(value);
	}

}
