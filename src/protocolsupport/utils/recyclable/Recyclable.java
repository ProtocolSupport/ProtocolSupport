package protocolsupport.utils.recyclable;

public interface Recyclable extends AutoCloseable {

	public static void recycle(Object object) {
		if (object instanceof Recyclable) {
			((Recyclable) object).recycle();
		}
	}

	public void recycle();

	@Override
	public default void close() {
		recycle();
	}

}
