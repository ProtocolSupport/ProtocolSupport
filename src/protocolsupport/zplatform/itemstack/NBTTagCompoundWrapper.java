package protocolsupport.zplatform.itemstack;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public abstract class NBTTagCompoundWrapper {

	public void writeToStream(OutputStream outputstream) throws IOException {
		writeToStream((DataOutput) new DataOutputStream(outputstream));
	}

	public abstract void writeToStream(DataOutput dataoutput) throws IOException;

	public abstract boolean isNull();

	public abstract void remove(String key);

	public static final int TYPE_STRING = 8;
	public static final int TYPE_COMPOUND = 10;
	public static final int TYPE_LIST = 9;

	public abstract Collection<String> getKeys();

	public abstract boolean hasKeyOfType(String tagname, int type);

	public abstract NBTTagCompoundWrapper getCompound(String key);

	public abstract void setCompound(String key, NBTTagCompoundWrapper compound);

	public abstract NBTTagListWrapper getList(String key, int type);

	public abstract void setList(String key, NBTTagListWrapper list);

	public abstract String getString(String key);

	public abstract void setString(String key, String value);

	public abstract int getNumber(String key);

	public abstract void setInt(String key, int i);

	public abstract void setByte(String key, int value);

}
