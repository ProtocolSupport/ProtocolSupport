package protocolsupport.protocol.types.nbt;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class NBTCompound extends NBT {

	@Override
	public NBTType<NBTCompound> getType() {
		return NBTType.COMPOUND;
	}

	protected final Map<String, NBT> tags = new LinkedHashMap<>();

	public Set<String> getTagNames() {
		return Collections.unmodifiableSet(tags.keySet());
	}

	public Map<String, NBT> getTags() {
		return Collections.unmodifiableMap(tags);
	}

	public NBT getTag(String key) {
		return tags.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> T getTagOfType(String key, NBTType<T> type) {
		NBT tag = tags.get(key);
		if ((tag != null) && (tag.getType() == type)) {
			return (T) tag;
		}
		return null;
	}

	public NBTNumber getNumberTag(String key) {
		NBT tag = tags.get(key);
		if ((tag != null) && NBTNumber.class.isAssignableFrom(tag.getType().getNBTTagClass())) {
			return (NBTNumber) tag;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> NBTList<T> getTagListOfType(String key, NBTType<T> type) {
		NBTList<? extends NBT> list = getTagOfType(key, NBTType.LIST);
		if ((list != null) && (list.getTagsType() == type)) {
			return (NBTList<T>) list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public NBTList<NBTNumber> getNumberTagList(String key) {
		NBTList<? extends NBT> list = getTagOfType(key, NBTType.LIST);
		if ((list != null) && NBTNumber.class.isAssignableFrom(list.getTagsType().getNBTTagClass())) {
			return (NBTList<NBTNumber>) list;
		}
		return null;
	}

	public NBT removeTag(String key) {
		return tags.remove(key);
	}

	public void setTag(String key, NBT tag) {
		if (tag != null) {
			tags.put(key, tag);
		} else {
			tags.remove(key);
		}
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof NBTCompound) && ((NBTCompound) other).tags.equals(tags);
	}

	@Override
	public int hashCode() {
		return tags.hashCode();
	}

	@Override
	public NBTCompound clone() {
		NBTCompound newCompound = new NBTCompound();
		tags.forEach((k, v) -> newCompound.setTag(k, v.clone()));
		return newCompound;
	}

}
