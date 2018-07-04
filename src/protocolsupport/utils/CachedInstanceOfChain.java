package protocolsupport.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CachedInstanceOfChain<T> {

	private final Map<Class<?>, T> knownPaths = new LinkedHashMap<>();
	private final Map<Class<?>, T> cachedPaths = new HashMap<>();

	public void setKnownPath(Class<?> clazz, T path) {
		knownPaths.put(clazz, path);
	}

	public T selectPath(Class<?> clazz) {
		return cachedPaths.computeIfAbsent(clazz, k ->
			knownPaths.entrySet().stream()
			.filter(entry -> entry.getKey().isAssignableFrom(k))
			.map(Map.Entry::getValue)
			.findFirst().orElse(null)
		);
	}

}
