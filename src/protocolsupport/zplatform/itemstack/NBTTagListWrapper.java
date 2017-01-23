package protocolsupport.zplatform.itemstack;

public abstract class NBTTagListWrapper {

	public abstract boolean isEmpty();

	public abstract int size();

	public abstract NBTTagCompoundWrapper getCompound(int index);

	public abstract void addCompound(NBTTagCompoundWrapper wrapper);

	public abstract String getString(int index);

	public abstract void addString(String value);

	public abstract int getNumber(int index);

	public abstract void addInt(int i);

	public abstract void addByte(int b);

}
