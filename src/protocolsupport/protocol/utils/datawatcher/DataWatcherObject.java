package protocolsupport.protocol.utils.datawatcher;

import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class DataWatcherObject<T> {

	protected T value;

	public abstract int getTypeId(ProtocolVersion version);

	public abstract void readFromStream(ProtocolSupportPacketDataSerializer serializer) throws DecoderException;

	public abstract void writeToStream(ProtocolSupportPacketDataSerializer serializer);

	public T getValue() {
		return value;
	}

}
