package protocolsupport.protocol.types.nbt;

public class NBTInt extends NBTNumber {

	@Override
	public NBTType<NBTInt> getType() {
		return NBTType.INT;
	}

	protected final int value;
	public NBTInt(int value) {
		this.value = value;
	}

	@Override
	public byte getAsByte() {
		return (byte) value;
	}

	@Override
	public short getAsShort() {
		return (short) value;
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
		return (other instanceof NBTInt) && (((NBTInt) other).value == value);
	}

	@Override
	public int hashCode() {
		return value;
	}

}
