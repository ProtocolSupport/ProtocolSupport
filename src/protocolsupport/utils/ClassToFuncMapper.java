package protocolsupport.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class ClassToFuncMapper<S, R, V> {

	private final HashSet<Class<?>> failedResolve = new HashSet<Class<?>>();
	private final HashMap<Class<?>, Func<S, R, V>> classToId = new HashMap<>();
	private boolean acceptingReg = true;

	public void register(Class<?> clazz, Func<S, R, V> func) {
		if (!acceptingReg) {
			throw new IllegalArgumentException("No longer accepting registrations");
		} 
		classToId.put(clazz, func);
	}

	public void stopAcceptingRegistrations() {
		acceptingReg = false;
	}

	public R runFunc(Class<?> clazz, S scope, V value) throws Exception {
		if (failedResolve.contains(clazz)) {
			return null;
		}
		Func<S, R, V> func = classToId.get(clazz);
		if (func != null) {
			return func.run(scope, value);
		}
		for (Entry<Class<?>, Func<S, R, V>> entry : classToId.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				classToId.put(clazz, func);
				return entry.getValue().run(scope, value);
			}
		}
		failedResolve.add(clazz);
		return null;
	}

	public abstract static class Func<T, R, V> {
		public abstract R run(T scope, V value) throws Exception;
	}

}
