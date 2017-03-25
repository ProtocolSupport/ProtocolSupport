package protocolsupport.protocol.utils.datawatcher;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class DataWatcherObject<T> {

	protected T value;

	public abstract void readFromStream(ByteBuf from, ProtocolVersion version) throws DecoderException;

	public abstract void writeToStream(ByteBuf to, ProtocolVersion version);

	public T getValue() {
		return value;
	}

}
