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
	public boolean equals(Object other) {
		return (other instanceof NBTByte) && (((NBTByte) other).value == value);
	}

	@Override
	public int hashCode() {
		return value;
	}

}
