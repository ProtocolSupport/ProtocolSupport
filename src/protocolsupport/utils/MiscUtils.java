package protocolsupport.utils;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import protocolsupport.utils.reflection.ReflectionUtils;

public class MiscUtils {

	private MiscUtils() {
	}

	public static @Nonnull String clampString(@Nonnull String string, @Nonnegative int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static int ceilToBase(@Nonnegative int number, @Nonnegative int base) {
		int ceil = (number / base) * base;
		if (number != ceil) {
			ceil += base;
		}
		return ceil;
	}

	public static long getColorDiff(int r1, int r2, int g1, int g2, int b1, int b2) {
		long rmean = (r1 + r2) / 2;
		long r = r1 - r2;
		long g = g1 - g2;
		long b = b1 - b2;
		return (((512 + rmean) * r * r) >> 8) + (4 * g * g) + (((767 - rmean) * b * b) >> 8);
	}

	public static final long currentTimeMillisFromNanoTime() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
	}

	public static void rethrowThreadException(Throwable t) {
		if (t instanceof ThreadDeath) {
			ReflectionUtils.sneakyThrow(t);
		}
		if ((t instanceof InterruptedException) && Thread.currentThread().isInterrupted()) {
			ReflectionUtils.sneakyThrow(t);
		}
	}

}
