package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;

import protocolsupport.api.ProtocolVersion;

public class ProtocolData<T> {

	protected final Map<ProtocolVersion, T> entries = new EnumMap<>(ProtocolVersion.class);

	protected ProtocolData() {
	}

	@SafeVarargs
	public ProtocolData(Map.Entry<T, ProtocolVersion[]>... entries) {
		for (Map.Entry<T, ProtocolVersion[]> entry : entries) {
			T data = entry.getKey();
			for (ProtocolVersion version : entry.getValue()) {
				this.entries.put(version, data);
			}
		}
	}

	public T get(ProtocolVersion version) {
		T entry = entries.get(version);
		if (entry == null) {
			throw new IllegalArgumentException(createMissingDataMessage(version));
		}
		return entries.get(version);
	}

	protected String createMissingDataMessage(ProtocolVersion version) {
		return MessageFormat.format("Missing data for protocol version {0}", version);
	}

}
