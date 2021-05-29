package protocolsupport.protocol.storage;

import java.net.InetAddress;

import org.bukkit.Bukkit;

import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import protocolsupport.utils.Utils;

public class ThrottleTracker {

	private ThrottleTracker() {
	}

	private static final long time = Bukkit.getConnectionThrottle();

	private static final Object2LongOpenHashMap<InetAddress> tracker = new Object2LongOpenHashMap<>();
	static {
		tracker.defaultReturnValue(-1);
	}

	public static boolean isEnabled() {
		return time > 0;
	}

	public static boolean throttle(InetAddress address) {
		synchronized (tracker) {
			long ctime = Utils.currentTimeMillisFromNanoTime();
			LongIterator iterator = tracker.values().iterator();
			while (iterator.hasNext()) {
				if (iterator.nextLong() < ctime) {
					iterator.remove();
				}
			}
			long ret = tracker.put(address, ctime + time);
			return ret != tracker.defaultReturnValue();
		}
	}

}
