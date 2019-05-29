package protocolsupport.protocol.types.nbt;

import java.util.Arrays;

public class NBTByteArray extends NBT {

	@Override
	public NBTType<NBTByteArray> getType() {
		return NBTType.BYTE_ARRAY;
	}

	protected final byte[] array;
	public NBTByteArray(byte[] array) {
		this.array = array;
	}

	public byte[] getValue() {
		return array;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof NBTByteArray) && Arrays.equals(((NBTByteArray) other).array, array);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(array);
	}

	@Override
	public NBTByteArray clone() {
		return new NBTByteArray(array.clone());
	}

}
