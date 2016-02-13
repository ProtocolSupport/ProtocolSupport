package protocolsupport.utils.recyclable;

import java.util.ArrayList;

import io.netty.util.Recycler;

public class RecyclableArrayList<E> extends ArrayList<E> implements RecyclableCollection<E> {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private static final Recycler<RecyclableArrayList> RECYCLER = new Recycler<RecyclableArrayList>() {
		@Override
		protected RecyclableArrayList newObject(Recycler.Handle handle) {
			return new RecyclableArrayList(handle);
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> RecyclableArrayList<T> create() {
		return RECYCLER.get();
	}

	private final Recycler.Handle handle;
	private RecyclableArrayList(Recycler.Handle handle) {
		this.handle = handle;
	}

	@Override
	public void recycle() {
		clear();
		RECYCLER.recycle(this, handle);
	}

}
