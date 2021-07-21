package protocolsupport.utils;

public class Vector3S {

	public static final Vector3S ZERO = new Vector3S((short) 0, (short) 0, (short) 0);

	private final short x;
	private final short y;
	private final short z;

	public Vector3S(short x, short y, short z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public short getX() {
		return x;
	}

	public short getY() {
		return y;
	}

	public short getZ() {
		return z;
	}


	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
