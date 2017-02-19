package protocolsupport.protocol.storage;

import java.net.InetAddress;

import org.bukkit.Bukkit;

import gnu.trove.iterator.TObjectLongIterator;
import gnu.trove.map.hash.TObjectLongHashMap;

public class ThrottleTracker {

	private static final long time = Bukkit.getConnectionThrottle();

	private static final TObjectLongHashMap<InetAddress> tracker = new TObjectLongHashMap<>();

	public static boolean isEnabled() {
		return time > 0;
	}

	public static boolean throttle(InetAddress address) {
		synchronized (tracker) {
			long ctime = System.currentTimeMillis();
			TObjectLongIterator<InetAddress> iterator = tracker.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				if (iterator.value() < ctime) {
					iterator.remove();
				}
			}
			long ret = tracker.put(address, ctime + time);
			return ret != tracker.getNoEntryValue();
		}
	}

}
