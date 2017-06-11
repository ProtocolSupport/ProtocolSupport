package protocolsupport.utils.recyclable;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;

public class RecyclableSingletonList<E> extends AbstractCollection<E> implements RecyclableCollection<E> {

	@SuppressWarnings("rawtypes")
	private static final Recycler<RecyclableSingletonList> recycle = new Recycler<RecyclableSingletonList>() {
		@SuppressWarnings("unchecked")
		@Override
		protected RecyclableSingletonList newObject(Handle<RecyclableSingletonList> handle) {
			return new RecyclableSingletonList(handle);
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> RecyclableSingletonList<T> create(T singleValue) {
		RecyclableSingletonList<T> list = recycle.get();
		list.singleValue = singleValue;
		return list;
	}

	@SuppressWarnings("rawtypes")
	private final Handle<RecyclableSingletonList> handle;
	@SuppressWarnings("rawtypes")
	private RecyclableSingletonList(Handle<RecyclableSingletonList> handle) {
		this.handle = handle;
	}

	protected E singleValue;

	@Override
	public void recycle() {
		if (singleValue instanceof Recyclable) {
			((Recyclable) singleValue).recycle();
		}
		recycleObjectOnly();
	}

	@Override
	public void recycleObjectOnly() {
		singleValue = null;
		handle.recycle(this);
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return singleValue.equals(o);
	}

	private final ResetableIterator iterator = new ResetableIterator();

	@Override
	public Iterator<E> iterator() {
		iterator.reset();
		return iterator;
	}

	protected class ResetableIterator implements Iterator<E> {
		private boolean hasNext = true;

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public E next() {
			if (hasNext) {
				hasNext = false;
				return singleValue;
			}
			throw new NoSuchElementException();
		}

		public void reset() {
			hasNext = true;
		}
	}

}
