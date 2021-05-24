package protocolsupport.protocol.types.nbt;

import java.util.Arrays;

public class NBTIntArray extends NBT {

	@Override
	public NBTType<NBTIntArray> getType() {
		return NBTType.INT_ARRAY;
	}

	protected final int[] array;

	public NBTIntArray(int[] array) {
		this.array = array;
	}

	public int[] getValue() {
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
		NBTIntArray other = (NBTIntArray) obj;
		return Arrays.equals(array, other.array);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(array);
	}

	@Override
	public NBTIntArray clone() {
		return new NBTIntArray(array.clone());
	}

}
