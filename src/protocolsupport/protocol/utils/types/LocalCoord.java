package protocolsupport.protocol.utils.types;

public class LocalCoord {

	private final byte x;
	private final int y;
	private final byte z;

	public LocalCoord(byte x, int y, byte z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public byte getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public byte getZ() {
		return z;
	}

	public static LocalCoord fromGlobal(Position position) {
		return new LocalCoord((byte) (position.getX() & 0xF), position.getY(), (byte) (position.getZ() & 0xF));
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LocalCoord)) {
			return false;
		}
		LocalCoord other = (LocalCoord) obj;
		return (x == other.x) && (y == other.y) && (z == other.z);
	}

	@Override
	public int hashCode() {
		return ((x * 31) + y) * 31 + z;
	}

}
