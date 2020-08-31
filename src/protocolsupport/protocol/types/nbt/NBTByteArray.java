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
		NBTByteArray other = (NBTByteArray) obj;
		return Arrays.equals(array, other.array);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(array);
	}

}
