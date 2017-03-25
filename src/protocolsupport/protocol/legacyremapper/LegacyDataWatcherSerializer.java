package protocolsupport.protocol.legacyremapper;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;

public class LegacyDataWatcherSerializer {

	public static void encodeData(ByteBuf to, ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> objects) {
		if (!objects.isEmpty()) {
			TIntObjectIterator<DataWatcherObject<?>> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				int tk = ((DataWatcherObjectIdRegistry.getTypeId(object, version) << 5) | (iterator.key() & 0x1F)) & 0xFF;
				to.writeByte(tk);
				object.writeToStream(to, version);
			}
		} else {
			to.writeByte(31);
			to.writeByte(0);
		}
		to.writeByte(127);
	}

}
