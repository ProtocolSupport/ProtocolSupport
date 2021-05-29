package protocolsupport.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CachedInstanceOfChain<T> {

	private final Map<Class<?>, T> knownPaths = new LinkedHashMap<>();
	private final Map<Class<?>, T> cachedPaths = new ConcurrentHashMap<>();

	public void setKnownPath(@Nonnull Class<?> clazz, @Nonnull T path) {
		knownPaths.put(clazz, path);
		cachedPaths.put(clazz, path);
	}

	public @Nullable T selectPath(@Nonnull Class<?> clazz) {
		return cachedPaths.computeIfAbsent(clazz, k ->
			knownPaths.entrySet().stream()
			.filter(entry -> entry.getKey().isAssignableFrom(k))
			.map(Map.Entry::getValue)
			.findFirst().orElse(null)
		);
	}

}
