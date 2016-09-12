package protocolsupport.protocol.storage;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import protocolsupport.utils.Utils;

public class ThrottleTracker {

	private static final long time = Utils.getServer().server.getConnectionThrottle();

	private static final Cache<InetAddress, Boolean> tracker = CacheBuilder.newBuilder()
	.concurrencyLevel(Utils.getJavaPropertyValue("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors(), Utils.Converter.STRING_TO_INT))
	.expireAfterWrite(time, TimeUnit.MILLISECONDS)
	.build();

	public static boolean isEnabled() {
		return time > 0;
	}

	public static boolean throttle(InetAddress address) {
		boolean result = tracker.getIfPresent(address) != null;
		tracker.put(address, Boolean.TRUE);
		return result;
	}

}
