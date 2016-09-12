package protocolsupport.protocol.utils.datawatcher;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class DataWatcherObject<T> {

	protected T value;

	public abstract int getTypeId(ProtocolVersion version);

	public abstract void readFromStream(ProtocolSupportPacketDataSerializer serializer) throws IOException;

	public abstract void writeToStream(ProtocolSupportPacketDataSerializer serializer);

	public T getValue() {
		return value;
	}

}
