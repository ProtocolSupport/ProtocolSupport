package protocolsupport.utils.datawatcher.objects;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.ItemStack;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.datawatcher.DataWatcherObject;

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
