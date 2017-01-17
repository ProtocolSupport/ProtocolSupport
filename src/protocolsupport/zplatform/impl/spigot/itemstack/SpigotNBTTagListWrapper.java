package protocolsupport.zplatform.impl.spigot.itemstack;

import java.util.Objects;

import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagString;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class SpigotNBTTagListWrapper extends NBTTagListWrapper {

	protected final NBTTagList tag;
	protected SpigotNBTTagListWrapper(NBTTagList tag) {
		this.tag = tag;
	}

	public static SpigotNBTTagListWrapper create() {
		return new SpigotNBTTagListWrapper(new NBTTagList());
	}

	@Override
	public boolean isEmpty() {
		return tag.isEmpty();
	}

	@Override
	public int size() {
		return tag.size();
	}

	@Override
	public NBTTagCompoundWrapper getCompound(int index) {
		return new SpigotNBTTagCompoundWrapper(tag.get(index));
	}

	@Override
	public void addCompound(NBTTagCompoundWrapper wrapper) {
		tag.add(((SpigotNBTTagCompoundWrapper) wrapper).tag);
	}

	@Override
	public String getString(int index) {
		return tag.getString(index);
	}

	@Override
	public void addString(String value) {
		tag.add(new NBTTagString(value));
	}

	@Override
	public int getNumber(int index) {
		return tag.c(index);
	}

	@Override
	public void addInt(int i) {
		tag.add(new NBTTagInt(i));
	}

	@Override
	public void addByte(int b) {
		tag.add(new NBTTagByte((byte) b));
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof SpigotNBTTagListWrapper)) {
			return false;
		}
		SpigotNBTTagListWrapper other = (SpigotNBTTagListWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return tag.toString();
	}

}
