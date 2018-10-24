package protocolsupport.api.utils;

public class Any<T1, T2> {

	private final T1 obj1;
	private final T2 obj2;

	public Any(T1 t1, T2 t2) {
		this.obj1 = t1;
		this.obj2 = t2;
	}

	public boolean hasObj1() {
		return obj1 != null;
	}

	public boolean hasObj2() {
		return obj2 != null;
	}

	public T1 getObj1() {
		return obj1;
	}

	public T2 getObj2() {
		return obj2;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + (hasObj1() ? obj1.hashCode() : 0);
		hash = 47 * hash + (hasObj2() ? obj2.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Any<?, ?> && obj.hashCode() == hashCode()) {
			Any<?, ?> anyObj = (Any<?, ?>) obj;
			return anyObj.getObj1().equals(obj1) && anyObj.getObj2().equals(getObj2());
		}
		return false;
	}

}
