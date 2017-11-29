package protocolsupport.protocol.utils.datawatcher;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.Utils;

public abstract class DataWatcherObject<T> {

	protected T value;

	public abstract void writeToStream(ByteBuf to, ProtocolVersion version, String locale);

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
