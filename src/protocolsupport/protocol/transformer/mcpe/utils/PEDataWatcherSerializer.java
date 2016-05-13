package protocolsupport.protocol.transformer.mcpe.utils;

import java.nio.ByteOrder;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;

public class PEDataWatcherSerializer {

	public static byte[] encode(TIntObjectMap<DataWatcherObject<?>> objects) {
		PacketDataSerializer serializer = new PacketDataSerializer(Allocator.allocateBuffer().order(ByteOrder.LITTLE_ENDIAN), ProtocolVersion.MINECRAFT_PE);
		try {
			if (!objects.isEmpty()) {
				TIntObjectIterator<DataWatcherObject<?>> iterator = objects.iterator();
				while (iterator.hasNext()) {
					iterator.advance();
					DataWatcherObject<?> object = iterator.value();
					try {
						int tk = ((object.getTypeId(ProtocolVersion.MINECRAFT_1_8) << 5) | (iterator.key() & 0x1F)) & 0xFF;
						serializer.writeByte(tk);
					} catch (IllegalStateException e) {
						throw new IllegalStateException("Unable to serialize datawathcer object at index "+ iterator.key(), e);
					}
					object.writeToStream(serializer);
				}
			} else {
				//write fake entry with type byte and index 31
				serializer.writeByte(31);
				serializer.writeByte(0);
			}
			serializer.writeByte(127);
			return ChannelUtils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

}
