package protocolsupport.protocol.utils.datawatcher.objects;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.ItemStack;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectItemStack extends DataWatcherObject<ItemStack> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		return 5;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) throws IOException {
		value = serializer.readItemStack();
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeItemStack(value);
	}

}
