package protocolsupport.utils.recyclable;

import java.io.Closeable;

public interface Recyclable extends Closeable {

	public void recycle();

	@Override
	public default void close() {
		recycle();
	}

}
