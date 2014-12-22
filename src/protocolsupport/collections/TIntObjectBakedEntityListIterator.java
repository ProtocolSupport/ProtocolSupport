package protocolsupport.collections;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import net.minecraft.server.v1_8_R1.Entity;

public class TIntObjectBakedEntityListIterator implements ListIterator<Entity> {

	private List<Entity> list;
	private int cursor;

	private int lastRet = -1;

	protected TIntObjectBakedEntityListIterator(List<Entity> list, int startingIndex) {
		this.list = list;
		this.cursor = startingIndex;
	}

	@Override
	public boolean hasNext() {
		return cursor != list.size();
	}

	@Override
	public Entity next() {
		try {
			int i = cursor;
			Entity next = list.get(i);
			lastRet = i;
			cursor = i + 1;
			return next;
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public boolean hasPrevious() {
		return cursor != 0;
	}

	@Override
	public Entity previous() {
		try {
			int i = cursor - 1;
			Entity previous = list.get(i);
			lastRet = cursor = i;
			return previous;
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public int nextIndex() {
		return cursor;
	}

	@Override
	public int previousIndex() {
		return cursor - 1;
	}

	@Override
	public void remove() {
		if (lastRet < 0) {
			throw new IllegalStateException();
		}
		try {
			list.remove(lastRet);
			if (lastRet < cursor)
				cursor--;
			lastRet = -1;
		} catch (IndexOutOfBoundsException e) {
			throw new ConcurrentModificationException();
		}
	}

	@Override
	public void set(Entity e) {
		if (lastRet < 0) {
			throw new IllegalStateException();
		}
		try {
			list.set(lastRet, e);
		} catch (IndexOutOfBoundsException ex) {
			throw new ConcurrentModificationException();
		}
	}

	@Override
	public void add(Entity e) {
		try {
			int i = cursor;
			list.add(i, e);
			lastRet = -1;
			cursor = i + 1;
		} catch (IndexOutOfBoundsException ex) {
			throw new ConcurrentModificationException();
		}
	}

}
