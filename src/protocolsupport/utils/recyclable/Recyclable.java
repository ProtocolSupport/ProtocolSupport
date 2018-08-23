package protocolsupport.utils.recyclable;

public interface Recyclable extends AutoCloseable {

	public void recycle();

	@Override
	public default void close() {
		recycle();
	}

}
