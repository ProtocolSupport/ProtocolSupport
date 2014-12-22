package protocolsupport.collections;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.server.v1_8_R1.Entity;

public class TIntObjectBakedEntitySubList extends AbstractList<Entity> {

	private List<Entity> list;
	private int offset;
	private int size;

	protected TIntObjectBakedEntitySubList(List<Entity> list, int fromIndex, int toIndex) {
		if (fromIndex < 0) {
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		}
		if (toIndex > list.size()) {
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		}
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
		}
		this.list = list;
		this.offset = fromIndex;
		this.size = toIndex - fromIndex;
	}

	@Override
	public Entity set(int index, Entity element) {
		return list.set(index + offset, element);
	}

	@Override
	public Entity get(int index) {
		return list.get(index + offset);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(int index, Entity element) {
		list.add(index + offset, element);
		size++;
	}

	@Override
	public Entity remove(int index) {
		Entity result = list.remove(index + offset);
		size--;
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends Entity> c) {
		return addAll(size, c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Entity> c) {
		int cSize = c.size();
		if (cSize == 0) {
			return false;
		}
		list.addAll(offset + index, c);
		size += cSize;
		return true;
	}

	public Iterator<Entity> iterator() {
		return listIterator();
	}

	public ListIterator<Entity> listIterator(final int index) {
		return new TIntObjectBakedEntityListIterator(this, index);
	}

	public List<Entity> subList(int fromIndex, int toIndex) {
		return new TIntObjectBakedEntitySubList(this, fromIndex, toIndex);
	}

}
