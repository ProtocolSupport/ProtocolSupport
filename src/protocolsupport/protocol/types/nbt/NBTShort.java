package protocolsupport.protocol.types.nbt;

public class NBTShort extends NBTNumber {

	@Override
	public NBTType<NBTShort> getType() {
		return NBTType.SHORT;
	}

	protected final short value;
	public NBTShort(short value) {
		this.value = value;
	}

	@Override
	public byte getAsByte() {
		return (byte) value;
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
		return (other instanceof NBTShort) && (((NBTShort) other).value == value);
	}

	@Override
	public int hashCode() {
		return value;
	}

}
