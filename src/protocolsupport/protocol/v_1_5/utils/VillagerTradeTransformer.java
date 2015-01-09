package protocolsupport.protocol.v_1_5.utils;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class VillagerTradeTransformer {

	public static ByteBuf to15VillagerTradeList(PacketDataSerializer data, ProtocolVersion newversion) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer(), newversion);
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
		return serializer.readBytes(serializer.readableBytes());
	}

}
