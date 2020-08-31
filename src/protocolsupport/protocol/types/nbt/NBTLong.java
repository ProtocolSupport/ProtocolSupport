package protocolsupport.protocol.types.nbt;

public class NBTLong extends NBTNumber {

	@Override
	public NBTType<NBTLong> getType() {
		return NBTType.LONG;
	}

	protected final long value;

	public NBTLong(long value) {
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
		NBTLong other = (NBTLong) obj;
		return value == other.value;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(value);
	}

}
