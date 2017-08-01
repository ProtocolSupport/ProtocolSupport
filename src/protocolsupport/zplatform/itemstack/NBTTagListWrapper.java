package protocolsupport.zplatform.itemstack;

public abstract class NBTTagListWrapper {

	public abstract NBTTagType getType();

	public abstract boolean isEmpty();

	public abstract int size();

	public abstract NBTTagCompoundWrapper getCompound(int index);

	public abstract NBTTagListWrapper getList(int index);

	public abstract String getString(int index);

	public abstract int getIntNumber(int index);

	public abstract long getLongNumber(int index);

	public abstract float getFloatNumber(int index);

	public abstract double getDoubleNumber(int index);

	public abstract byte[] getByteArray(int index);

	public abstract int[] getIntArray(int index);

	public abstract long[] getLongArray(int index);

	public abstract void addCompound(NBTTagCompoundWrapper wrapper);

	public abstract void addList(NBTTagListWrapper wrapper);

	public abstract void addString(String value);

	public abstract void addByte(int value);

	public abstract void addShort(int value);

	public abstract void addInt(int value);

	public abstract void addLong(long value);

	public abstract void addFloat(float value);

	public abstract void addDouble(double value);

	public abstract void addByteArray(byte[] value);

	public abstract void addIntArray(int[] value);

	public abstract void addLongArray(long[] value);

}
