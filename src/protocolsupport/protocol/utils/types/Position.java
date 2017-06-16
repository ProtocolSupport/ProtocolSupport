package protocolsupport.protocol.utils.types;

import protocolsupport.utils.Utils;

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
		return ((getX() & 0x3FFFFFFL) << 38) | ((getY() & 0xFFFL) << 26) | (getZ() & 0x3FFFFFFL);
	}

	public static Position fromLong(long n) {
		return new Position((int) (n >> 38), (int) ((n >> 26) & 0xFFF), (int) ((n << 38) >> 38));
	}

	public void modifyX(int cnt) {
		x += cnt;
	}

	public void modifyY(int cnt) {
		y += cnt;
	}

	public void modifyZ(int cnt) {
		z += cnt;
	}

	public Position clone() {
		return new Position(x, y, z);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
