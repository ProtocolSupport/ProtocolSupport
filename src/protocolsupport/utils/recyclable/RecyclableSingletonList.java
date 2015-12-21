package protocolsupport.utils.recyclable;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import io.netty.util.Recycler;

public class RecyclableSingletonList<E> extends AbstractCollection<E> implements RecyclableCollection<E> {

	@SuppressWarnings("rawtypes")
	private static final Recycler<RecyclableSingletonList> RECYCLER = new Recycler<RecyclableSingletonList>() {
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

	private E singleValue;

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

	@Override
	public Iterator<E> iterator() {
		return singletonIterator(singleValue);
	}

	static <E> Iterator<E> singletonIterator(final E e) {
		return new Iterator<E>() {
			private boolean hasNext = true;

			@Override
			public boolean hasNext() {
				return hasNext;
			}

			@Override
			public E next() {
				if (hasNext) {
					hasNext = false;
					return e;
				}
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
