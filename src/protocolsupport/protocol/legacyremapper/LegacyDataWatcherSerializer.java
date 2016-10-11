package protocolsupport.protocol.legacyremapper;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class LegacyDataWatcherSerializer {

	public static void encodeData(TIntObjectMap<DataWatcherObject<?>> objects, ProtocolSupportPacketDataSerializer serializer) {
		if (!objects.isEmpty()) {
			TIntObjectIterator<DataWatcherObject<?>> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				int tk = ((object.getTypeId(serializer.getVersion()) << 5) | (iterator.key() & 0x1F)) & 0xFF;
				serializer.writeByte(tk);
				object.writeToStream(serializer);
			}
		} else {
			serializer.writeByte(31);
			serializer.writeByte(0);
		}
		serializer.writeByte(127);
	}

}
