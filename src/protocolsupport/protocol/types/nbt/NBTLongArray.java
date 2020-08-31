package protocolsupport.protocol.types.nbt;

import java.util.Arrays;

public class NBTLongArray extends NBT {

	@Override
	public NBTType<NBTLongArray> getType() {
		return NBTType.LONG_ARRAY;
	}

	protected final long[] array;

	public NBTLongArray(long[] array) {
		this.array = array;
	}

	public long[] getValue() {
		return array;
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
		NBTLongArray other = (NBTLongArray) obj;
		return Arrays.equals(array, other.array);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(array);
	}

}
