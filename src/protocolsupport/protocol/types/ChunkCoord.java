package protocolsupport.protocol.types;

import protocolsupport.utils.Utils;

public class ChunkCoord {

	public static ChunkCoord fromGlobal(int x, int z) {
		return new ChunkCoord(x >> 4, z >> 4);
	}

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

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
