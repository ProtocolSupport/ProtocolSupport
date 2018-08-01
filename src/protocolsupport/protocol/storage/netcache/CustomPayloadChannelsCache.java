package protocolsupport.protocol.storage.netcache;

import java.util.HashMap;
import java.util.Map;

public class CustomPayloadChannelsCache {

	protected final Map<String, String> modernToLegacy = new HashMap<>();

	public void register(String modern, String legacy) {
		modernToLegacy.put(modern, legacy);
	}

	public void unregister(String modern) {
		modernToLegacy.remove(modern);
	}

	public String getLegacyName(String modern) {
		return modernToLegacy.getOrDefault(modern, modern);
	}

}
