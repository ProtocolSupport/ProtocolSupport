package com.github.shevchik.protocolsupport.protocol.transformer.v_1_7.utils;

import java.io.IOException;

import com.github.shevchik.protocolsupport.api.ProtocolVersion;
import com.github.shevchik.protocolsupport.protocol.PacketDataSerializer;
import com.github.shevchik.protocolsupport.utils.Allocator;
import com.github.shevchik.protocolsupport.utils.Utils;

public class VillagerTradeTransformer {

	public static byte[] to17VillagerTradeList(PacketDataSerializer data, ProtocolVersion newversion) throws IOException {
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
