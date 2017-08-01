package protocolsupport.zplatform.impl.spigot.itemstack;

import java.util.Objects;

import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagByte;
import net.minecraft.server.v1_12_R1.NBTTagByteArray;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagIntArray;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagLongArray;
import net.minecraft.server.v1_12_R1.NBTTagShort;
import net.minecraft.server.v1_12_R1.NBTTagString;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class SpigotNBTTagListWrapper extends NBTTagListWrapper {

	protected final NBTTagList tag;
	protected SpigotNBTTagListWrapper(NBTTagList tag) {
		this.tag = tag;
	}

	public static SpigotNBTTagListWrapper create() {
		return new SpigotNBTTagListWrapper(new NBTTagList());
	}

	@Override
	public NBTTagType getType() {
		return NBTTagType.fromId(tag.g());
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
	public NBTTagListWrapper getList(int index) {
        if ((index >= 0) && (index < size())) {
            NBTBase nbtBase = tag.i(index);
            if (nbtBase.getTypeId() == NBTTagType.LIST.getId()) {
                return new SpigotNBTTagListWrapper((NBTTagList) nbtBase);
            }
        }
        return SpigotNBTTagListWrapper.create();
	}

	@Override
	public String getString(int index) {
		return tag.getString(index);
	}

	@Override
	public int getIntNumber(int index) {
		return tag.c(index);
	}

	@Override
	public long getLongNumber(int index) {
        if ((index >= 0) && (index < size())) {
            NBTBase nbtBase = tag.i(index);
            if (nbtBase.getTypeId() == NBTTagType.LONG.getId()) {
                return ((NBTTagLong) nbtBase).d();
            }
        }
        return 0;
	}

	@Override
	public float getFloatNumber(int index) {
		return tag.g(index);
	}

	@Override
	public double getDoubleNumber(int index) {
		return tag.f(index);
	}

	@Override
	public byte[] getByteArray(int index) {
        if ((index >= 0) && (index < size())) {
            NBTBase nbtBase = tag.i(index);
            if (nbtBase.getTypeId() == NBTTagType.BYTE_ARRAY.getId()) {
                return ((NBTTagByteArray) nbtBase).c();
            }
        }
        return new byte[0];
	}

	@Override
	public int[] getIntArray(int index) {
		return tag.d(index);
	}

	@Override
	public long[] getLongArray(int index) {
		//TODO: actually implement this
		return new long[0];
	}

	@Override
	public void addCompound(NBTTagCompoundWrapper wrapper) {
		tag.add(((SpigotNBTTagCompoundWrapper) wrapper).tag);
	}

	@Override
	public void addList(NBTTagListWrapper wrapper) {
		tag.add(((SpigotNBTTagListWrapper) wrapper).tag);
	}

	@Override
	public void addString(String value) {
		tag.add(new NBTTagString(value));
	}

	@Override
	public void addByte(int b) {
		tag.add(new NBTTagByte((byte) b));
	}

	@Override
	public void addShort(int value) {
		tag.add(new NBTTagShort((short) value));
	}

	@Override
	public void addInt(int i) {
		tag.add(new NBTTagInt(i));
	}

	@Override
	public void addLong(long value) {
		tag.add(new NBTTagLong(value));
	}

	@Override
	public void addFloat(float value) {
		tag.add(new NBTTagFloat(value));
	}

	@Override
	public void addDouble(double value) {
		tag.add(new NBTTagDouble(value));
	}

	@Override
	public void addByteArray(byte[] value) {
		tag.add(new NBTTagByteArray(value));
	}

	@Override
	public void addIntArray(int[] value) {
		tag.add(new NBTTagIntArray(value));
	}

	@Override
	public void addLongArray(long[] value) {
		tag.add(new NBTTagLongArray(value));
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
