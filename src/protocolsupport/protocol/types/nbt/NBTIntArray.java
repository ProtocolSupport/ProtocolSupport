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
	public boolean equals(Object other) {
		return (other instanceof NBTIntArray) && Arrays.equals(((NBTIntArray) other).array, array);
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
