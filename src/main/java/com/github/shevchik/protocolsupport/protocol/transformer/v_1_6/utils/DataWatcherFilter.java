package com.github.shevchik.protocolsupport.protocol.transformer.v_1_6.utils;

import gnu.trove.map.TIntObjectMap;

import java.io.IOException;
import java.util.Iterator;

import com.github.shevchik.protocolsupport.api.ProtocolVersion;
import com.github.shevchik.protocolsupport.protocol.watchedentity.WatchedEntity;
import com.github.shevchik.protocolsupport.utils.DataWatcherSerializer;
import com.github.shevchik.protocolsupport.utils.Utils;
import com.github.shevchik.protocolsupport.utils.DataWatcherSerializer.DataWatcherObject;
import com.github.shevchik.protocolsupport.utils.DataWatcherSerializer.DataWatcherObject.ValueType;

public class DataWatcherFilter {

	public static byte[] filterEntityData(ProtocolVersion version, WatchedEntity entity, byte[] data) throws IOException {
		if (entity == null) {
			return data;
		}
		TIntObjectMap<DataWatcherObject> objects = DataWatcherSerializer.decodeData(version, data);
		if (entity.isLiving()) {
			DataWatcherObject nametag = objects.remove(2);
			if (nametag != null) {
				nametag.value = Utils.clampString((String) nametag.value, 64);
				objects.put(10, nametag);
			}
			DataWatcherObject nametagvisible = objects.remove(3);
			if (nametagvisible != null) {
				objects.put(11, nametagvisible);
			}
		}
		if (entity.isAgeable()) {
			DataWatcherObject object = objects.get(12);
			if (object != null) {
				object.toInt();
			}
		} else if (entity.isEnderman()) {
			DataWatcherObject object = objects.get(16);
			if (object != null) {
				object.toByte();
			}
		} else if (entity.isMinecart()) {
			DataWatcherObject object = objects.get(20);
			if (object != null) {
				int value = (int) object.value;
				int p1 = value & 0xFFFF;
				int p2 = value >> 12;
				object.value = (p2 << 16) | p1;
			}
		} else if (entity.isItemFrame()) {
			if (objects.containsKey(8)) {
				objects.put(2, objects.get(8));
			}
			if (objects.containsKey(9)) {
				int rotation = (byte) objects.get(9).value;
				objects.put(3, new DataWatcherObject(ValueType.BYTE, ((byte) (rotation >> 1))));
			}
		}
		// remove type 7 watched objects
		Iterator<DataWatcherObject> iterator = objects.valueCollection().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().type == ValueType.VECTOR3F) {
				iterator.remove();
			}
		}
		// add object in case objects list is empty
		if (objects.isEmpty()) {
			objects.put(31, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		}
		return DataWatcherSerializer.encodeData(version, objects);
	}

}
