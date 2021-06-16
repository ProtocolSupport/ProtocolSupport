package protocolsupport.protocol.types;

import java.util.Objects;

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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ChunkCoord other = (ChunkCoord) obj;
		return (x == other.x) && (z == other.z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, z);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
