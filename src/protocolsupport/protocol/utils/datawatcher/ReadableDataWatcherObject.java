package protocolsupport.protocol.utils.datawatcher;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class ReadableDataWatcherObject<T> extends DataWatcherObject<T> {

	public abstract void readFromStream(ByteBuf from, ProtocolVersion version, String locale);

}
