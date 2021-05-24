package protocolsupport.protocol.types.nbt;

public class NBTFloat extends NBTNumber {

	@Override
	public NBTType<NBTFloat> getType() {
		return NBTType.FLOAT;
	}

	protected final float value;

	public NBTFloat(float value) {
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
		return value;
	}

	@Override
	public double getAsDouble() {
		return value;
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
		NBTFloat other = (NBTFloat) obj;
		return Float.floatToIntBits(value) == Float.floatToIntBits(other.value);
	}

	@Override
	public int hashCode() {
		return Float.hashCode(value);
	}

	@Override
	public NBT clone() {
		return new NBTFloat(value);
	}

}
