package protocolsupport.protocol.types.nbt;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NBTList<T extends NBT> extends NBT {

	@Override
	@SuppressWarnings("rawtypes")
	public NBTType<NBTList> getType() {
		return NBTType.LIST;
	}

	protected final NBTType<T> type;
	protected final List<T> tags = new ArrayList<>();

	public NBTList(NBTType<T> type) {
		this.type = type;
	}

	public NBTList(NBTType<T> type, List<T> tags) {
		this.type = type;
		this.tags.addAll(tags);
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

	public void addTag(T tag) {
		if (type != tag.getType()) {
			throw new IllegalArgumentException(MessageFormat.format(
				"Invalid tag type. Expected {0}, got {1}.",
				type.getNBTTagClass(),
				tag.getType().getNBTTagClass()
			));
		}
		tags.add(tag);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof NBTList) {
			NBTList<?> list = (NBTList<?>) other;
			return (list.type == type) && list.tags.equals(tags);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return type.hashCode() + tags.hashCode();
	}

	@Override
	public NBTList<T> clone() {
		List<T> newTags = new ArrayList<>(tags.size());
		tags.forEach(x -> newTags.add((T) x.clone()));
		return new NBTList<>(type, newTags);
	}

}
