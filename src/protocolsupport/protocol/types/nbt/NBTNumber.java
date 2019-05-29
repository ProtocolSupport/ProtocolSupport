package protocolsupport.protocol.types.nbt;

public abstract class NBTNumber extends NBT {

	public abstract byte getAsByte();

	public abstract short getAsShort();

	public abstract int getAsInt();

	public abstract long getAsLong();

	public abstract float getAsFloat();

	public abstract double getAsDouble();

	@Override
	public NBTNumber clone() {
		return this;
	}

}
