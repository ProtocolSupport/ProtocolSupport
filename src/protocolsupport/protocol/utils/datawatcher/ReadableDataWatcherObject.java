package protocolsupport.protocol.utils.datawatcher;

import io.netty.buffer.ByteBuf;

public abstract class ReadableDataWatcherObject<T> extends DataWatcherObject<T> {

	public abstract void readFromStream(ByteBuf from);

}
