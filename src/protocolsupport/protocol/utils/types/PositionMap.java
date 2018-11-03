package protocolsupport.protocol.utils.types;

import java.util.HashMap;
import java.util.Map;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public interface PositionMap<T> extends Map<ChunkCoord, PositionMap.LocalMap<T>> {

	public PositionMap.LocalMap<T> newLocalMap();

	public default T getAtPosition(Position position) {
		return get(position.getChunkCoord(), position.getLocalCoord());
	}

	public default void putAtPosition(Position position, T value) {
		set(position.getChunkCoord(), position.getLocalCoord(), value);
	}

	public default void removeAtPosition(Position position) {
		remove(position.getChunkCoord(), position.getLocalCoord());
	}

	public default T get(ChunkCoord chunkCoord, int localCoord) {
		LocalMap<T> localMap = get(chunkCoord);
		if (localMap != null) {
			return localMap.get(localCoord);
		}
		return null;
	}

	public default void set(ChunkCoord chunkCoord, int localCoord, T value) {
		computeIfAbsent(chunkCoord, k -> newLocalMap()).put(localCoord, value);
	}

	public default void remove(ChunkCoord chunkCoord, int localCoord) {
		computeIfPresent(chunkCoord, (key, map) -> {
			map.remove(localCoord);
			return map;
		});
	}

	public default void removeChunk(ChunkCoord chunkCoord) {
		remove(chunkCoord);
	}



	public static interface LocalMap<T> extends Map<Integer, T> {

		public static class LocalIntMap extends Int2IntOpenHashMap implements LocalMap<Integer> {
			private static final long serialVersionUID = 1L;
		}

	}

	public static class PositionIntMap extends HashMap<ChunkCoord, LocalMap<Integer>> implements PositionMap<Integer> {

		private static final long serialVersionUID = 1L;

		@Override
		public PositionMap.LocalMap<Integer> newLocalMap() {
			return new LocalMap.LocalIntMap();
		}

	}

}
