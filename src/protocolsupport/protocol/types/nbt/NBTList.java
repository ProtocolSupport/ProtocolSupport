package protocolsupport.protocol.types.nbt;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class NBTList<T extends NBT> extends NBT implements Iterable<T> {

	public static NBTList<NBTCompound> createCompoundList() {
		return new NBTList<>(NBTType.COMPOUND);
	}

	public static NBTList<NBTString> createStringList() {
		return new NBTList<>(NBTType.STRING);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public NBTType<NBTList> getType() {
		return NBTType.LIST;
	}

	protected final NBTType<T> type;
	protected final List<T> tags;

	public NBTList(NBTType<T> type) {
		this.type = type;
		this.tags = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public NBTList(NBTType<T> type, List<T> tags) {
		this.type = type;
		this.tags = new ArrayList<>(tags.size());
		for (T tag : tags) {
			this.tags.add((T) tag.clone());
		}
	}

	public NBTType<T> getTagsType() {
		return type;
	}

	public boolean isEmpty() {
		return tags.isEmpty();
	}

	public int size() {
		return tags.size();
	}

	public List<T> getTags() {
		return Collections.unmodifiableList(tags);
	}

	public T getTag(int index) {
		return tags.get(index);
	}

	public void setTag(int index, T tag) {
		validateAddTag(tag);
		tags.set(index, tag);
	}

	public void addTag(int index, T tag) {
		validateAddTag(tag);
		tags.add(index, tag);
	}

	public void addTag(T tag) {
		validateAddTag(tag);
		tags.add(tag);
	}

	public T removeTag(int index) {
		return tags.remove(index);
	}

	protected void validateAddTag(T tag) {
		if (type != tag.getType()) {
			throw new IllegalArgumentException(MessageFormat.format("Invalid tag type. Expected {0}, got {1}.", type.getNBTClass(), tag.getClass()));
		}
	}

	@Override
	public Iterator<T> iterator() {
		return tags.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NBTList<T> other = (NBTList<T>) obj;
		return Objects.equals(type, other.type) && Objects.equals(tags, other.tags);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, tags);
	}

	@Override
	public NBTList<T> clone() {
		return new NBTList<>(type, tags);
	}

}
