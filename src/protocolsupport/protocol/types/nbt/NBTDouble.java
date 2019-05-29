package protocolsupport.protocol.types.nbt;

public class NBTDouble extends NBTNumber {

	@Override
	public NBTType<NBTDouble> getType() {
		return NBTType.DOUBLE;
	}

	protected final double value;
	public NBTDouble(double value) {
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
		return (int) value;
	}

	@Override
	public long getAsLong() {
		return (long) value;
	}

	@Override
	public float getAsFloat() {
		return (float) value;
	}

	@Override
	public double getAsDouble() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof NBTDouble) && (((NBTDouble) other).value == value);
	}

	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}

}
