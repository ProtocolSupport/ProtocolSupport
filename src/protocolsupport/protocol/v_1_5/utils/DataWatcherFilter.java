package protocolsupport.protocol.v_1_5.utils;

import gnu.trove.map.TIntObjectMap;

import java.io.IOException;
import java.util.Iterator;

import net.minecraft.server.v1_8_R2.ItemStack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.watchedentites.WatchedEntity;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.DataWatcherSerializer.DataWatcherObject;

public class DataWatcherFilter {

	public static byte[] filterEntityData(ProtocolVersion version, WatchedEntity entity, byte[] data) throws IOException {
		if (entity == null) {
			return data;
		}
		TIntObjectMap<DataWatcherObject> objects = DataWatcherSerializer.decodeData(version, data);
		if (entity.isLiving()) {
			objects.remove(5);
			objects.remove(6);
			DataWatcherObject nametag = objects.remove(2);
			DataWatcherObject nametagvisible = objects.remove(3);
			if (nametag != null) {
				objects.put(5, nametag);
			}
			if (nametagvisible != null) {
				objects.put(6, nametagvisible);
			}
			objects.remove(10);
			DataWatcherObject arrows = objects.remove(9);
			DataWatcherObject pambient = objects.remove(8);
			DataWatcherObject pcolor = objects.remove(7);
			if (arrows != null) {
				objects.put(10, arrows);
			}
			if (pambient != null) {
				objects.put(9, pambient);
			}
			if (pcolor != null) {
				objects.put(8, pcolor);
			}
		}
		if (entity.isWolf()) {
			DataWatcherObject damageobject = objects.get(18);
			if (damageobject != null) {
				damageobject.value = ((int) ((float) damageobject.value));
				damageobject.type = 2;
			}
		}
		if (entity.isAgeable()) {
			DataWatcherObject object = objects.get(12);
			if (object != null) {
				object.value = ((int) ((byte) object.value));
				object.type = 2;
			}
		} else if (entity.isEnderman()) {
			DataWatcherObject object = objects.get(16);
			if (object != null) {
				object.value = ((byte) ((short) object.value));
				object.type = 0;
			}
		} else if (entity.isMinecart()) {
			DataWatcherObject damageobject = objects.get(19);
			if (damageobject != null) {
				damageobject.value = ((int) ((float) damageobject.value));
				damageobject.type = 2;
			}
			DataWatcherObject object = objects.get(20);
			if (object != null) {
				int value = (int) object.value;
				int p1 = value & 0xFFFF;
				int p2 = value >> 12;
				object.value = (p2 << 16) | p1;
			}
		} else if (entity.isBoat()) {
			DataWatcherObject damageobject = objects.get(19);
			if (damageobject != null) {
				damageobject.value = ((int) ((float) damageobject.value));
				damageobject.type = 2;
			}
		} else if (entity.isItemFrame()) {
			if (objects.containsKey(8)) {
				ItemStack item = (ItemStack) objects.get(8).value;
				objects.put(2, new DataWatcherObject(5, item));
			}
			if (objects.containsKey(9)) {
				int rotation = (byte) objects.get(9).value;
				objects.put(3, new DataWatcherObject(0, ((byte) (rotation >> 1))));
			}
		}
		// remove type 7 watched objects
		Iterator<DataWatcherObject> iterator = objects.valueCollection().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().type == 7) {
				iterator.remove();
			}
		}
		// add object in case objects list is empty
		if (objects.isEmpty()) {
			objects.put(31, new DataWatcherObject(0, (byte) 0));
		}
		return DataWatcherSerializer.encodeData(version, objects);
	}

}
