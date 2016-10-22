package protocolsupport.protocol.utils.types;

import java.util.Objects;

import net.minecraft.server.v1_10_R1.NBTTagByte;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.NBTTagString;

public class NBTTagListWrapper {

	private NBTTagList tag;

	private NBTTagListWrapper(NBTTagList tag) {
		this.tag = tag;
	}

	public static NBTTagListWrapper wrap(NBTTagList tag) {
		return new NBTTagListWrapper(tag);
	}

	public static NBTTagListWrapper create() {
		return new NBTTagListWrapper(new NBTTagList());
	}

	public NBTTagList unwrap() {
		return this.tag;
	}

	public boolean isEmpty() {
		return tag.isEmpty();
	}

	public int size() {
		return tag.size();
	}

	public NBTTagCompoundWrapper getCompound(int index) {
		return NBTTagCompoundWrapper.wrap(tag.get(index));
	}

	public void addCompound(NBTTagCompoundWrapper wrapper) {
		tag.add(wrapper.unwrap());
	}

	public String getString(int index) {
		return tag.getString(index);
	}

	public void addString(String value) {
		tag.add(new NBTTagString(value));
	}

	public int getNumber(int index) {
		return tag.c(index);
	}

	public void addInt(int i) {
		tag.add(new NBTTagInt(i));
	}

	public void addByte(int b) {
		tag.add(new NBTTagByte((byte) b));
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof NBTTagListWrapper)) {
			return false;
		}
		NBTTagListWrapper other = (NBTTagListWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return tag.toString();
	}

}
