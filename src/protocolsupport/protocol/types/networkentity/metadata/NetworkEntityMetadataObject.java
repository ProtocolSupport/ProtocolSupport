package protocolsupport.protocol.types.networkentity.metadata;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.reflection.ReflectionUtils;

public abstract class NetworkEntityMetadataObject<T> {

	protected T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public abstract void writeToStream(ByteBuf to, ProtocolVersion version, String locale);

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
