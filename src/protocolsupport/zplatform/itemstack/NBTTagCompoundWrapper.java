package protocolsupport.zplatform.itemstack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang3.NotImplementedException;

import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagCompoundWrapper;

public abstract class NBTTagCompoundWrapper {


	public static NBTTagCompoundWrapper fromJson(String json) {
		switch (ServerPlatform.get()) {
			case SPIGOT: {
				return SpigotNBTTagCompoundWrapper.fromJson(json);
			}
			default: {
				//TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static NBTTagCompoundWrapper fromStream(DataInput datainput) throws IOException {
		switch (ServerPlatform.get()) {
			case SPIGOT: {
				return SpigotNBTTagCompoundWrapper.fromStream(datainput);
			}
			default: {
				//TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static NBTTagCompoundWrapper createEmpty() {
		switch (ServerPlatform.get()) {
			case SPIGOT: {
				return SpigotNBTTagCompoundWrapper.createEmpty();
			}
			default: {
				//TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static NBTTagCompoundWrapper createNull() {
		switch (ServerPlatform.get()) {
			case SPIGOT: {
				return SpigotNBTTagCompoundWrapper.createNull();
			}
			default: {
				//TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
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
