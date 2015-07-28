package protocolsupport.protocol.transformer.v_1_6.utils;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.Utils;

public class VillagerTradeTransformer {

	public static byte[] to16VillagerTradeList(PacketDataSerializer data, ProtocolVersion newversion) throws IOException {
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer(), newversion);
		try {
			serializer.writeInt(data.readInt());
			int count = data.readByte() & 0xFF;
			serializer.writeByte(count);
			for (int i = 0; i < count; i++) {
				serializer.writeItemStack(data.readItemStack());
				serializer.writeItemStack(data.readItemStack());
				boolean hasItem2 = data.readBoolean();
				serializer.writeBoolean(hasItem2);
				if (hasItem2) {
					serializer.writeItemStack(data.readItemStack());
				}
				serializer.writeBoolean(data.readBoolean());
				data.readInt();
				data.readInt();
			}
			return Utils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

}
