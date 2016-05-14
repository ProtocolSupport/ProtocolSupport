package protocolsupport.protocol.utils.types;

import net.minecraft.server.v1_9_R2.MathHelper;

public class Position {

	protected int x;
	protected int y;
	protected int z;

	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public long asLong() {
		return ((getX() & i) << h) | ((getY() & j) << g) | (getZ() & k);
	}

	public static Position fromLong(long n) {
		return new Position((int) ((n << (64 - h - c)) >> (64 - c)), (int) ((n << (64 - g - f)) >> (64 - f)), (int) ((n << (64 - d)) >> (64 - d)));
	}

	private static int c = 1 + MathHelper.e(MathHelper.c(30000000));
	private static int d = c;
	private static int f = 64 - c - d;
	private static int g = d;
	private static int h = g + f;
	private static long i = (1L << c) - 1L;
	private static long j = (1L << f) - 1L;
	private static long k = (1L << d) - 1L;

}
