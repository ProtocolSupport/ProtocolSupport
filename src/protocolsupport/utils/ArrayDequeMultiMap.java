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
 * Used to store binary-sorted key-value(s) pairs with key-values.
 * Entries containing one or more important values will appear at the two-dimensional top.
 */
public class ArrayDequeMultiMap<K, V> {

	private final ArrayDeque<Entry<K, ChildDeque<V>>> map = new ArrayDeque<Entry<K, ChildDeque<V>>>();
	
	public ArrayDeque<Entry<K, ChildDeque<V>>> entrySet() {
		return map;
	}
	
	public Predicate<Entry<K, ChildDeque<V>>> keyMatches(K key) {
		System.out.println("Hash key given: " + key.hashCode());
		return e -> e.getKey().hashCode() == key.hashCode() && e.getKey().equals(key);
	}
	
	public Predicate<Entry<K, ChildDeque<V>>> hasValue(V value) {
		return e -> e.getValue().parallelStream().filter(v -> v.hashCode() == value.hashCode() && v.equals(value)).count() > 0;
	}
	
	public Optional<Entry<K, ChildDeque<V>>> findEntry(K key) {
		System.out.println("Finding with key " + key);
		map.parallelStream().forEach(e -> {
			System.out.println(e.getKey().hashCode());
			System.out.println(e.getKey().equals(key));
		});
		return map.parallelStream().filter(keyMatches(key)).findAny();
	}
	
	public Optional<Entry<K, ChildDeque<V>>> findEntryContainingValue(V value) {
		return map.parallelStream().filter(hasValue(value)).findAny();
	}
	
	public void clear() {
		map.clear();
	}
	
	public boolean containsKey(K key) {
		return findEntry(key).isPresent();
	}
	
	public boolean containsValue(V value) {
		return findEntryContainingValue(value).isPresent();
	}
	
	public Entry<K, ChildDeque<V>> prepareNewEntry(K key) {
		Entry<K, ChildDeque<V>> entry = new AbstractMap.SimpleEntry<K, ChildDeque<V>>(key, new ChildDeque<V>());
		map.addLast(entry);
		return entry;
	}
	
	public void put(K key, V value) {
		put(key, value, false);
	}
	
	public void put(K key, V value, boolean important) {
		System.out.println("Putting <" + key.toString() + ", " + value.toString() + ">");
		Entry<K, ChildDeque<V>> entry = findEntry(key).orElse(prepareNewEntry(key));
		System.out.println(entry.getValue());
		System.out.println(value);
		if(important) {
			entry.getValue().addFirst(value);
			if(!keyMatches(entry.getKey()).test(map.getFirst())) {
				map.remove(entry);
				map.addFirst(entry);
			}
		} else {
			entry.getValue().addLast(value);
		}
		System.out.println(entry.getValue().size());
		System.out.println(map.size());
	}
	
	public void remove(K key) {
		findEntry(key).ifPresent(entry -> map.remove(entry));
	}
	
	public ChildDeque<V> get(K key) {
		Optional<Entry<K, ChildDeque<V>>> optionalEntry = findEntry(key);
		if(optionalEntry.isPresent()) {
			return optionalEntry.get().getValue();
		} else {
			return null;
		}
	}
	
	public V getVeryFirst() {
		if(map.peekFirst() == null) {
			return null;
		}
		return map.getFirst().getValue().peekFirst();
	}
	
	public V getVeryLast() {
		if(map.peekLast() == null) { 
			return null;
		}
		return map.getLast().getValue().peekLast();
	}
	
	public void cycleDown(BiFunction<K, ChildDeque<V>, Boolean> func) {
		cycle(map.iterator(), func);
	}
	
	public void cycleUp(BiFunction<K, ChildDeque<V>, Boolean> func) {
		cycle(map.descendingIterator(), func);
	}
	
	private void cycle(Iterator<Entry<K, ChildDeque<V>>> itorator, BiFunction<K, ChildDeque<V>, Boolean> func) {
		while(itorator.hasNext()) {
			Entry<K, ChildDeque<V>> e = itorator.next();
			if(func.apply(e.getKey(), e.getValue())) {
				itorator.remove();
			}
		}
	}
	
	public static class ChildDeque<T> extends ArrayDeque<T> {
		private static final long serialVersionUID = -5508375911312087313L;

		public void cycleDown(Function<T, Boolean> func) {
			cycle(iterator(), func);
		}
		
		public void cycleUp(Function<T, Boolean> func) {
			cycle(descendingIterator(), func);
		}
		
		private void cycle(Iterator<T> itorator, Function<T, Boolean> func) {
			while(itorator.hasNext()) {
				T e = itorator.next();
				if(func.apply(e)) {
					itorator.remove();
				}
			}
		}
	}
	
}
