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
		NBTInt other = (NBTInt) obj;
		return value == other.value;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(value);
	}

}
