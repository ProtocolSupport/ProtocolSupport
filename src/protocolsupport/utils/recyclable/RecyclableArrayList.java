package protocolsupport.utils.recyclable;

import java.util.ArrayList;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;

public class RecyclableArrayList<E> extends ArrayList<E> implements RecyclableCollection<E> {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private static final Recycler<RecyclableArrayList> recycler = new Recycler<RecyclableArrayList>() {
		@SuppressWarnings("unchecked")
		@Override
		protected RecyclableArrayList newObject(Handle<RecyclableArrayList> handle) {
			return new RecyclableArrayList(handle);
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> RecyclableArrayList<T> create() {
		return recycler.get();
	}

	@SuppressWarnings("rawtypes")
	private final Handle<RecyclableArrayList> handle;
	@SuppressWarnings("rawtypes")
	private RecyclableArrayList(Handle<RecyclableArrayList> handle) {
		this.handle = handle;
	}

	@Override
	public void recycle() {
		for (E element : this) {
			if (element instanceof Recyclable) {
				((Recyclable) element).recycle();
			}
		}
		recycleObjectOnly();
	}

	@Override
	public void recycleObjectOnly() {
		clear();
		handle.recycle(this);
	}

}
