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
	public boolean equals(Object other) {
		return (other instanceof NBTLongArray) && Arrays.equals(((NBTLongArray) other).array, array);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(array);
	}

	@Override
	public NBTLongArray clone() {
		return new NBTLongArray(array.clone());
	}

}
