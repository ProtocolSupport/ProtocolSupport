package protocolsupport.utils.recyclable;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RecyclableEmptyList<E> implements RecyclableCollection<E> {

	@SuppressWarnings("rawtypes")
	private static final RecyclableEmptyList instance = new RecyclableEmptyList();

	@SuppressWarnings("unchecked")
	public static <T> RecyclableEmptyList<T> get() {
		return instance;
	}

	private RecyclableEmptyList() {
	}

	@Override
	public void recycle() {
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	private static final Object[] EMPTY_ARRAY = new Object[0];

	@Override
	public Object[] toArray() {
		return EMPTY_ARRAY;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length > 0) {
			a[0] = null;
		}
		return a;
	}

	@Override
	public boolean add(E e) {
        throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
        throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
        throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> iterator() {
		return (Iterator<E>) EmptyIterator.EMPTY_ITERATOR;
	}

	private static class EmptyIterator<E> implements Iterator<E> {
		static final EmptyIterator<Object> EMPTY_ITERATOR = new EmptyIterator<>();

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public E next() {
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	}

}
