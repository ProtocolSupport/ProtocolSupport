package protocolsupport.zplatform.impl.spigot.itemstack;

import java.util.Collection;
import java.util.Objects;

import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLongArray;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class SpigotNBTTagCompoundWrapper extends NBTTagCompoundWrapper {

	protected final NBTTagCompound tag;
	protected SpigotNBTTagCompoundWrapper(NBTTagCompound tag) {
		this.tag = tag;
	}

	public final NBTTagCompound unwrap() {
		return tag;
	}

	public static NBTTagCompoundWrapper fromJson(String json) {
		try {
			return new SpigotNBTTagCompoundWrapper(MojangsonParser.parse(json));
		} catch (MojangsonParseException e) {
			SneakyThrow.sneaky(e);
		}
		return null;
	}

	public static SpigotNBTTagCompoundWrapper createEmpty() {
		return new SpigotNBTTagCompoundWrapper(new NBTTagCompound());
	}

	public static SpigotNBTTagCompoundWrapper wrap(NBTTagCompound tag) {
		return new SpigotNBTTagCompoundWrapper(tag);
	}

	@Override
	public boolean isNull() {
		return tag == null;
	}

	@Override
	public void remove(String key) {
		tag.remove(key);
	}

	@Override
	public Collection<String> getKeys() {
		return tag.c();
	}

	@Override
	public NBTTagType getTagType(String tagname) {
		NBTBase base = tag.get(tagname);
		return base != null ? NBTTagType.fromId(base.getTypeId()) : null;
	}

	@Override
	public boolean hasKeyOfType(String tagname, NBTTagType i) {
		return tag.hasKeyOfType(tagname, i.getId());
	}

	@Override
	public NBTTagCompoundWrapper getCompound(String key) {
		return new SpigotNBTTagCompoundWrapper(tag.getCompound(key));
	}

	@Override
	public NBTTagListWrapper getList(String key) {
        NBTBase nbtBase = tag.get(key);
        if ((nbtBase != null) && (nbtBase.getTypeId() == NBTTagType.LIST.getId())) {
            return new SpigotNBTTagListWrapper((NBTTagList) nbtBase);
        }
        return SpigotNBTTagListWrapper.create();
	}

	@Override
	public NBTTagListWrapper getList(String key, NBTTagType type) {
		return new SpigotNBTTagListWrapper(tag.getList(key, type.getId()));
	}

	@Override
	public String getString(String key) {
		return tag.getString(key);
	}

	@Override
	public int getIntNumber(String key) {
		return tag.getInt(key);
	}

	@Override
	public byte getByteNumber(String key) {
		return tag.getByte(key);
	}

	@Override
	public long getLongNumber(String key) {
		return tag.getLong(key);
	}

	@Override
	public float getFloatNumber(String key) {
		return tag.getFloat(key);
	}

	@Override
	public double getDoubleNumber(String key) {
		return tag.getDouble(key);
	}

	@Override
	public byte[] getByteArray(String key) {
		return tag.getByteArray(key);
	}

	@Override
	public int[] getIntArray(String key) {
		return tag.getIntArray(key);
	}

	@Override
	public long[] getLongArray(String key) {
		//TODO: actually implement this
		return new long[0];
	}

	@Override
	public void setCompound(String key, NBTTagCompoundWrapper compound) {
		tag.set(key, ((SpigotNBTTagCompoundWrapper) compound).tag);
	}

	@Override
	public void setList(String key, NBTTagListWrapper list) {
		tag.set(key, ((SpigotNBTTagListWrapper) list).tag);
	}

	@Override
	public void setString(String key, String value) {
		tag.setString(key, value);
	}

	@Override
	public void setByte(String key, int value) {
		tag.setByte(key, (byte) value);
	}

	@Override
	public void setShort(String key, int value) {
		tag.setShort(key, (short) value);
	}

	@Override
	public void setInt(String key, int value) {
		tag.setInt(key, value);
	}

	@Override
	public void setLong(String key, long value) {
		tag.setLong(key, value);
	}

	@Override
	public void setFloat(String key, float value) {
		tag.setFloat(key, value);
	}

	@Override
	public void setDouble(String key, double value) {
		tag.setDouble(key, value);
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		tag.setByteArray(key, value);
	}

	@Override
	public void setIntArray(String key, int[] value) {
		tag.setIntArray(key, value);
	}

	@Override
	public void setLongArray(String key, long[] value) {
		tag.set(key, new NBTTagLongArray(value));
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof SpigotNBTTagCompoundWrapper)) {
			return false;
		}
		SpigotNBTTagCompoundWrapper other = (SpigotNBTTagCompoundWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return Objects.toString(tag);
	}

}
