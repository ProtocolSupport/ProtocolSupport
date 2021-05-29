package protocolsupport.utils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CollectionsUtils {

	private CollectionsUtils() {
	}

	public static @Nonnull <K, V extends Enum<V>> Map<K, V> makeEnumMappingMap(@Nonnull Class<V> e, @Nonnull Function<V, K> mapping) {
		HashMap<K, V> map = new HashMap<>();
		for (V v : e.getEnumConstants()) {
			map.put(mapping.apply(v), v);
		}
		return map;
	}

	public static @Nonnull <K extends Enum<K>, V extends Enum<V>> Map<K, V> makeEnumMappingEnumMap(@Nonnull Class<V> e, @Nonnull Class<K> k, @Nonnull Function<V, K> mapping) {
		EnumMap<K, V> map = new EnumMap<>(k);
		for (V v : e.getEnumConstants()) {
			map.put(mapping.apply(v), v);
		}
		return map;
	}

	public static @Nonnull <K extends Enum<K>, V extends Enum<V>> Map<K, V> makeEnumMappingEnumMap(@Nonnull Stream<V> stream, @Nonnull Class<K> k, @Nonnull Function<V, K> mapping) {
		EnumMap<K, V> map = new EnumMap<>(k);
		stream.forEach(v -> map.put(mapping.apply(v), v));
		return map;
	}

	public static @Nonnull <V extends Enum<V>> ArrayMap<V> makeEnumMappingArrayMap(@Nonnull Class<V> e, @Nonnull ToIntFunction<V> mapping) {
		return new ArrayMap<>(Arrays.stream(e.getEnumConstants()).map(c -> new ArrayMap.Entry<>(mapping.applyAsInt(c), c)).collect(Collectors.toList()));
	}

	public static @Nonnull <V extends Enum<V>> ArrayMap<V> makeEnumMappingArrayMap(@Nonnull Stream<V> stream, @Nonnull ToIntFunction<V> mapping) {
		return new ArrayMap<>(stream.map(c -> new ArrayMap.Entry<>(mapping.applyAsInt(c), c)).collect(Collectors.toList()));
	}

	public static class ArrayMap<T> {

		private final int offset;
		private final Object[] array;

		public ArrayMap(@Nonnegative int size) {
			this.array = new Object[size];
			this.offset = 0;
		}

		public ArrayMap(@Nonnull Collection<Entry<T>> entries) {
			int minKey = entries.stream().min(Comparator.comparingInt(e -> e.key)).get().key;
			int maxKey = entries.stream().max(Comparator.comparingInt(e -> e.key)).get().key;
			this.offset = -minKey;
			this.array = new Object[(maxKey - minKey) + 1];
			entries.stream().forEach(entry -> put(entry.key, entry.value));
		}

		@SafeVarargs
		public ArrayMap(@Nonnull Entry<T>... entries) {
			this(Arrays.asList(entries));
		}

		public int getMinKey() {
			return -offset;
		}

		public int getMaxKey() {
			return getMinKey() + array.length;
		}

		@SuppressWarnings("unchecked")
		public @Nullable T get(@Nonnegative int key) {
			return (T) Utils.getFromArrayOrNull(array, key + offset);
		}

		public void put(@Nonnegative int key, @Nullable T value) {
			int aindex = key + offset;
			if ((aindex >= array.length) || (aindex < 0)) {
				throw new IllegalArgumentException(MessageFormat.format("Cant fit key {0} in size {1} and offset {2}", key, array.length, offset));
			}
			array[aindex] = value;
		}

		public void clear() {
			Arrays.fill(array, null);
		}

		public static class Entry<T> {
			private final int key;
			private final T value;
			public Entry(@Nonnegative int key, @Nonnull T value) {
				this.key = key;
				this.value = value;
			}
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}

}
