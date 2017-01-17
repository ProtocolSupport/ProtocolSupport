package protocolsupport.zplatform.itemstack;

import org.apache.commons.lang3.NotImplementedException;

import protocolsupport.zplatform.ServerImplementationType;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagListWrapper;

public abstract class NBTTagListWrapper {

	public static NBTTagListWrapper create() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return SpigotNBTTagListWrapper.create();
			}
			default: {
				//TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

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
