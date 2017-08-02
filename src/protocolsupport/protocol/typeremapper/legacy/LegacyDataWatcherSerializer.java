package protocolsupport.protocol.typeremapper.legacy;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class LegacyDataWatcherSerializer {

	public static void encodeData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<DataWatcherObject<?>> objects) {
		boolean hadObject = false;
		for (int key = objects.getMinKey(); key < objects.getMaxKey(); key++) {
			DataWatcherObject<?> object = objects.get(key);
			if (object != null) {
				hadObject = true;
				int tk = ((DataWatcherObjectIdRegistry.getTypeId(object, version) << 5) | (key & 0x1F)) & 0xFF;
				to.writeByte(tk);
				object.writeToStream(to, version, locale);
			}
		}
		if (!hadObject) {
			to.writeByte(31);
			to.writeByte(0);
		}
		to.writeByte(127);
	}

}
