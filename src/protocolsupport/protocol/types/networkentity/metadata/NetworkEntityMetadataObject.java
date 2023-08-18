package protocolsupport.protocol.types.networkentity.metadata;

import protocolsupport.utils.reflection.ReflectionUtils;

public abstract class NetworkEntityMetadataObject<T> {

	protected T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
