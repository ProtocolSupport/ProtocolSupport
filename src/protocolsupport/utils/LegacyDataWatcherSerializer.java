package protocolsupport.utils;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.RecyclablePacketDataSerializer;
import protocolsupport.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.netty.ChannelUtils;

public class LegacyDataWatcherSerializer {

	public static byte[] encodeData(ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> objects) {
		RecyclablePacketDataSerializer serializer = RecyclablePacketDataSerializer.create(version);
		try {
			TIntObjectIterator<DataWatcherObject<?>> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				final int tk = ((object.getTypeId(version) << 5) | (iterator.key() & 0x1F)) & 0xFF;
				serializer.writeByte(tk);
				object.writeToStream(serializer);
			}
			serializer.writeByte(127);
			return ChannelUtils.toArray(serializer);
		} finally {
			serializer.release();
		}
	}

}
