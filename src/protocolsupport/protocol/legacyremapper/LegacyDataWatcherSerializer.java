package protocolsupport.protocol.legacyremapper;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.RecyclableProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.netty.ChannelUtils;

public class LegacyDataWatcherSerializer {

	public static byte[] encodeData(ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> objects) {
		RecyclableProtocolSupportPacketDataSerializer serializer = RecyclableProtocolSupportPacketDataSerializer.create(version);
		try {
			if (!objects.isEmpty()) {
				TIntObjectIterator<DataWatcherObject<?>> iterator = objects.iterator();
				while (iterator.hasNext()) {
					iterator.advance();
					DataWatcherObject<?> object = iterator.value();
					int tk = ((object.getTypeId(version) << 5) | (iterator.key() & 0x1F)) & 0xFF;
					serializer.writeByte(tk);
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
