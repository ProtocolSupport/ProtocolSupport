package protocolsupport.protocol.storage;

import java.net.InetAddress;

import gnu.trove.iterator.TObjectLongIterator;
import gnu.trove.map.hash.TObjectLongHashMap;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class ThrottleTracker {

	private static final TObjectLongHashMap<InetAddress> tracker = new TObjectLongHashMap<InetAddress>();
	private static final long time = MinecraftServer.getServer().server.getConnectionThrottle();

	public static boolean isEnabled() {
		return time > 0;
	}

	public static void track(InetAddress address, long time) {
		synchronized (tracker) {
			tracker.put(address, time);
			if (tracker.size() > 100) {
				long currentTime = System.currentTimeMillis();
				TObjectLongIterator<InetAddress> iterator = tracker.iterator();
				while (iterator.hasNext()) {
					iterator.advance();
					if ((currentTime - iterator.value()) < time) {
						iterator.remove();
					}
				}
			}
		}
	}

	public static boolean isThrottled(InetAddress address) {
		synchronized (tracker) {
			return tracker.containsKey(address) && ((System.currentTimeMillis() - tracker.get(address)) < time);
		}
	}

}
