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
	public int hashCode() {
		return Double.hashCode(value);
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
		NBTDouble other = (NBTDouble) obj;
		return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}

}
