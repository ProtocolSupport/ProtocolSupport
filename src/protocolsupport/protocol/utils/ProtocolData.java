package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnull;

import protocolsupport.api.ProtocolVersion;

public class ProtocolData<T> {

	protected final Map<ProtocolVersion, T> entries = new EnumMap<>(ProtocolVersion.class);

	protected ProtocolData() {
	}

	@SafeVarargs
	public ProtocolData(@Nonnull Map.Entry<T, ProtocolVersion[]>... entries) {
		for (Map.Entry<T, ProtocolVersion[]> entry : entries) {
			T data = entry.getKey();
			for (ProtocolVersion version : entry.getValue()) {
				this.entries.put(version, data);
			}
		}
	}

	public @Nonnull T get(@Nonnull ProtocolVersion version) {
		T entry = entries.get(version);
		if (entry == null) {
			throw new IllegalArgumentException(createMissingDataMessage(version));
		}
		return entries.get(version);
	}

	protected @Nonnull String createMissingDataMessage(@Nonnull ProtocolVersion version) {
		return MessageFormat.format("Missing data for protocol version {0}", version);
	}

}
