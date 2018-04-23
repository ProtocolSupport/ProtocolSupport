package protocolsupport.utils;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/***
 * Simple implementation of a double deque based map. 
 * (Pulled straight out of my thump. Actually I don't know if this is the best way, but it's A way)
 * Used to store binary-sorted key-value(s) pairs with key-values.
 * Entries containing one or more important values will appear at the two-dimensional top.
 */
public class ArrayDequeMultiMap<K, V> {

	private final ArrayDeque<Entry<K, ChildDeque<V>>> map = new ArrayDeque<Entry<K, ChildDeque<V>>>();

	/**
	 * Clears the multimap.
	 */
	public void clear() {
		map.clear();
	}

	/***
	 * @return if the multimap is empty.
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/***
	 * Checks if the multimap contains a key.
	 * @param key
	 * @return true, if the key exists in the map.
	 */
	public boolean containsKey(K key) {
		return findEntry(key).isPresent();
	}

	/***
	 * Checks if the multimap contains a value.
	 * @param value
	 * @return true, if the value exists in the map.
	 */
	public boolean containsValue(V value) {
		return findEntryContainingValue(value).isPresent();
	}

	/***
	 * Puts a non-important value in the ArrayDequeMultiMap.
	 * Non important entries are always inserted at the bottom.
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		put(key, value, false);
	}

	/**
	 * Puts entries in the ArrayDequeMultiMap.
	 * Important values will be put on top, others on the bottom.
	 * If a key newly obtains an important value, it will be put on top itself as will the value.
	 * @param key
	 * @param value
	 * @param important
	 */
	public void put(K key, V value, boolean important) {
		Optional<Entry<K, ChildDeque<V>>> optionalEntry = findEntry(key);
		Entry<K, ChildDeque<V>> entry;
		if (optionalEntry.isPresent()) {
			entry = optionalEntry.get();
		} else {
			entry = prepareNewEntry(key);
		}
		if (important) {
			entry.getValue().addFirst(value);
			if (!keyMatches(entry.getKey()).test(map.getFirst())) {
				remove(key);
				map.addFirst(entry);
			}
		} else {
			entry.getValue().addLast(value);
		}
	}

	/**
	 * Removes all entries beloning to key.
	 * @param key
	 */
	public void remove(K key) {
		findEntry(key).ifPresent(entry -> map.remove(entry));
	}

	/**
	 * Removes value belonging to key in multimap 
	 * and key if its childdeque is empty.
	 * @param key
	 * @param value
	 */
	public void remove(K key, V value) {
		findEntry(key).ifPresent(entry -> {
			entry.getValue().remove(value);
			if (entry.getValue().isEmpty()) {
				map.remove(entry);
			}
		});
	}

	/**
	 * Removes the very first element.
	 */
	public void removeVeryFirst() {
		if (map.peekFirst() != null) {
			map.getFirst().getValue().removeFirst();
		}
	}

	/**
	 * Removes the very last element.
	 */
	public void removeVeryLast() {
		if (map.peekLast() != null) {
			map.getLast().getValue().removeLast();
		}
	}

	/**
	 * Get's the entries belonging to the key.
	 * @param key
	 * @return
	 * The entries beloning to key, null if the key isn't present.
	 * (Empty maps are returned as is)
	 */
	public ChildDeque<V> get(K key) {
		Optional<Entry<K, ChildDeque<V>>> optionalEntry = findEntry(key);
		if (optionalEntry.isPresent()) {
			return optionalEntry.get().getValue();
		} else {
			return null;
		}
	}

	/***
	 * @return the first key in the map.
	 */
	public K getFirstKey() {
		if (map.peekFirst() == null) {
			return null;
		}
		return map.getFirst().getKey();
	}

	/***
	 * @return the last key in the map.
	 */
	public K getLastKey() {
		if (map.peekLast() == null) {
			return null;
		}
		return map.getLast().getKey();
	}

	/***
	 * @return the very first entry of the multimap, null if none exists.
	 */
	public V getVeryFirst() {
		if (map.peekFirst() == null) {
			return null;
		}
		return map.getFirst().getValue().peekFirst();
	}

	/***
	 * @return the very last entry of the multimap, null if none exists.
	 */
	public V getVeryLast() {
		if (map.peekLast() == null) { 
			return null;
		}
		return map.getLast().getValue().peekLast();
	}

	/**
	 * Cycles down (top to bottom) the multimap and removes value if true is returned.
	 * @param func
	 * the function to run with the value references.
	 */
	public void cycleDown(BiFunction<K, ChildDeque<V>, Boolean> func) {
		cycle(map.iterator(), func);
	}

	/**
	 * Cycles up (bottom to top) the multimap and removes value if true is returned.
	 * @param func
	 * the function to run on the value references.
	 */
	public void cycleUp(BiFunction<K, ChildDeque<V>, Boolean> func) {
		cycle(map.descendingIterator(), func);
	}

	private Entry<K, ChildDeque<V>> prepareNewEntry(K key) {
		Entry<K, ChildDeque<V>> entry = new AbstractMap.SimpleEntry<K, ChildDeque<V>>(key, new ChildDeque<V>());
		map.addLast(entry);
		return entry;
	}

	private void cycle(Iterator<Entry<K, ChildDeque<V>>> itorator, BiFunction<K, ChildDeque<V>, Boolean> func) {
		while (itorator.hasNext()) {
			Entry<K, ChildDeque<V>> e = itorator.next();
			if (func.apply(e.getKey(), e.getValue())) {
				itorator.remove();
			}
		}
	}

	private Predicate<Entry<K, ChildDeque<V>>> keyMatches(K key) {
		return e -> e.getKey().hashCode() == key.hashCode() && e.getKey().equals(key);
	}

	private Predicate<Entry<K, ChildDeque<V>>> hasValue(V value) {
		return e -> e.getValue().parallelStream().filter(v -> v.hashCode() == value.hashCode() && v.equals(value)).count() > 0;
	}

	private Optional<Entry<K, ChildDeque<V>>> findEntry(K key) {
		return map.stream().filter(keyMatches(key)).findFirst();
	}

	private Optional<Entry<K, ChildDeque<V>>> findEntryContainingValue(V value) {
		return map.stream().filter(hasValue(value)).findAny();
	}

	public static class ChildDeque<T> extends ArrayDeque<T> {

		private static final long serialVersionUID = -5508375911312087313L;

		/**
		 * Get if the deque has at least a non-null first element.
		 * @return
		 */
		public boolean hasFirst() {
			return !isEmpty() && peekFirst() != null;
		}

		/**
		 * Cycles down (top to bottom) the child deque and removes value if true is returned.
		 * @param func
		 * the function to run with the value references.
		 */
		public void cycleDown(Function<T, Boolean> func) {
			cycle(iterator(), func);
		}

		/**
		 * Cycles up (bottom to top) the child deque and removes value if true is returned.
		 * @param func
		 * the function to run on the value references.
		 */
		public void cycleUp(Function<T, Boolean> func) {
			cycle(descendingIterator(), func);
		}

		private void cycle(Iterator<T> itorator, Function<T, Boolean> func) {
			while(itorator.hasNext()) {
				T e = itorator.next();
				if (func.apply(e)) {
					itorator.remove();
				}
			}
		}

	}

}
