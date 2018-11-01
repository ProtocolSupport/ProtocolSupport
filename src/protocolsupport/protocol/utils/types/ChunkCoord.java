package protocolsupport.protocol.utils.types;

public class ChunkCoord {

	private final int x;
	private final int z;

	public ChunkCoord(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public static ChunkCoord fromGlobal(Position position) {
		return new ChunkCoord(position.getX() >> 4, position.getZ() >> 4);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ChunkCoord)) {
			return false;
		}
		ChunkCoord other = (ChunkCoord) obj;
		return (x == other.x) && (z == other.z);
	}

	@Override
	public int hashCode() {
		return (x * 31) + z;
	}

}
