package protocolsupport.protocol.utils.datawatcher;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.Utils;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class DataWatcherObject<T> {

	protected T value;

	public abstract void readFromStream(ByteBuf from, ProtocolVersion version, String locale) throws DecoderException;

	public abstract void writeToStream(ByteBuf to, ProtocolVersion version, String locale);

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
