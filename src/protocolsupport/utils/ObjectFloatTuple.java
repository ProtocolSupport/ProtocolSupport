package protocolsupport.utils;

public class ObjectFloatTuple<T> {

	protected final T o;
	protected final float f;

	public ObjectFloatTuple(T o1, float f) {
		this.o = o1;
		this.f = f;
	}

	public T getObject() {
		return o;
	}

	public float getFloat() {
		return f;
	}

}
