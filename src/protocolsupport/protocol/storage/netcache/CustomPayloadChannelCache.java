package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;

public class CustomPayloadChannelCache {

	protected final HashMap<String, String> modernToLegacy = new HashMap<>();

	public void register(String modern, String legacy) {
		modernToLegacy.put(modern, legacy);
	}

	public void unregister(String payloadModernTag) {
		modernToLegacy.remove(payloadModernTag);
	}

	public String getLegacyName(String modern) {
		return modernToLegacy.getOrDefault(modern, modern);
	}

}
