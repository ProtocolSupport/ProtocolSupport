package protocolsupport.utils.recyclable;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import io.netty.util.Recycler;

public class RecyclableSingletonList<E> extends AbstractCollection<E> implements RecyclableCollection<E> {

	@SuppressWarnings("rawtypes")
	private static final Recycler<RecyclableSingletonList> RECYCLER = new Recycler<RecyclableSingletonList>() {
		@Override
		protected RecyclableSingletonList newObject(Recycler.Handle handle) {
			return new RecyclableSingletonList(handle);
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> RecyclableSingletonList<T> create(T singleValue) {
		RecyclableSingletonList<T> list = RECYCLER.get();
		list.singleValue = singleValue;
		return list;
	}

	private final Recycler.Handle handle;
	private RecyclableSingletonList(Recycler.Handle handle) {
		this.handle = handle;
	}

	protected E singleValue;

	@Override
	public void recycle() {
		singleValue = null;
		RECYCLER.recycle(this, handle);
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
