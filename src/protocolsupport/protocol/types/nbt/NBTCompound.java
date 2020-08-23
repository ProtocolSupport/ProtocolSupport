package protocolsupport.protocol.types.nbt;

import java.text.MessageFormat;
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
	public <T extends NBT> T getTagOfTypeOrThrow(String key, NBTType<T> type) {
		NBT tag = tags.get(key);
		if (tag == null) {
			throw new IllegalStateException(MessageFormat.format("NBT {0} does not exist", key));
		}
		if (tag.getType() != type) {
			throw new IllegalStateException(MessageFormat.format("NBT {0} has unexpected type, expected {1}, but got {2}", key, type, tag.getType()));
		}
		return (T) tag;
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> T getTagOfTypeOrNull(String key, NBTType<T> type) {
		NBT tag = tags.get(key);
		if ((tag != null) && (tag.getType() == type)) {
			return (T) tag;
		}
		return null;
	}

	public NBTNumber getNumberTagOrThrow(String key) {
		NBT tag = tags.get(key);
		if (tag == null) {
			throw new IllegalStateException(MessageFormat.format("NBT {0} does not exist", key));
		}
		if (!NBTNumber.class.isAssignableFrom(tag.getType().getNBTClass())) {
			throw new IllegalStateException(MessageFormat.format("NBT {0} has unexpected type, expected NBTNumber, but got {1}", key, tag.getType()));
		}
		return (NBTNumber) tag;
	}

	public NBTNumber getNumberTagOrNull(String key) {
		NBT tag = tags.get(key);
		if ((tag != null) && NBTNumber.class.isAssignableFrom(tag.getType().getNBTClass())) {
			return (NBTNumber) tag;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> NBTList<T> getTagListOfTypeOrThrow(String key, NBTType<T> type) {
		NBTList<? extends NBT> list = getTagOfTypeOrThrow(key, NBTType.LIST);
		if (list.getTagsType() != type) {
			throw new IllegalStateException(MessageFormat.format("NBTList {0} tags type has unexpected type, expected {1}, but got {2}", key, type, list.getTagsType()));
		}
		return (NBTList<T>) list;
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> NBTList<T> getTagListOfTypeOrNull(String key, NBTType<T> type) {
		NBTList<? extends NBT> list = getTagOfTypeOrNull(key, NBTType.LIST);
		if ((list != null) && (list.getTagsType() == type)) {
			return (NBTList<T>) list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public NBTList<NBTNumber> getNumberTagListOrNull(String key) {
		NBTList<? extends NBT> list = getTagOfTypeOrNull(key, NBTType.LIST);
		if ((list != null) && NBTNumber.class.isAssignableFrom(list.getTagsType().getNBTClass())) {
			return (NBTList<NBTNumber>) list;
		}
		return null;
	}

	public String getStringTagValueOrThrow(String key) {
		return getTagOfTypeOrThrow(key, NBTType.STRING).getValue();
	}

	public String getStringTagValueOrNull(String key) {
		NBT tag = tags.get(key);
		if ((tag != null) && (tag.getType() == NBTType.STRING)) {
			return ((NBTString) tag).getValue();
		}
		return null;
	}

	public String getStringTagValueOrDefault(String key, String defaultValue) {
		NBT tag = tags.get(key);
		if ((tag != null) && (tag.getType() == NBTType.STRING)) {
			return ((NBTString) tag).getValue();
		}
		return defaultValue;
	}

	public NBT removeTag(String key) {
		return tags.remove(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> T removeTagAndReturnIfType(String key, NBTType<T> type) {
		NBT tag = removeTag(key);
		if ((tag != null) && (tag.getType() == type)) {
			return (T) tag;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends NBT> NBTList<T> removeTagAndReturnIfListType(String key, NBTType<T> type) {
		NBT tag = removeTag(key);
		if ((tag != null) && (tag.getType() == NBTType.LIST)) {
			NBTList<?> listTag = (NBTList<?>) tag;
			if (listTag.getTagsType() == type) {
				return (NBTList<T>) listTag;
			}
		}
		return null;
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

}
