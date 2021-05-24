package protocolsupport.protocol.types.nbt;

public class NBTByte extends NBTNumber {

	@Override
	public NBTType<NBTByte> getType() {
		return NBTType.BYTE;
	}

	protected final byte value;

	public NBTByte(byte value) {
		this.value = value;
	}

	public NBTByte(boolean value) {
		this((byte) (value ? 1 : 0));
	}

	@Override
	public byte getAsByte() {
		return value;
	}

	@Override
	public short getAsShort() {
		return value;
	}

	@Override
	public int getAsInt() {
		return value;
	}

	@Override
	public long getAsLong() {
		return value;
	}

	@Override
	public float getAsFloat() {
		return value;
	}

	@Override
	public double getAsDouble() {
		return value;
	}

	@Override
	public int hashCode() {
		return Byte.hashCode(value);
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
		NBTByte other = (NBTByte) obj;
		return value == other.value;
	}

	@Override
	public NBTByte clone() {
		return new NBTByte(value);
	}

}
