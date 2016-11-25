package protocolsupport.protocol.utils.registry;

import java.util.NoSuchElementException;

import protocolsupport.utils.nms.NetworkListenerState;

@SuppressWarnings("unchecked")
public class MiddleTransformerRegistry<T> {

	private static final int listenerStateLength = NetworkListenerState.values().length;

	private final LazyNewInstance<T>[] registry = new LazyNewInstance[listenerStateLength * 256];
	private InitCallBack<T> callback;

	public void register(NetworkListenerState state, int packetId, Class<? extends T> packetTransformer) {
		registry[toKey(state, packetId)] = new LazyNewInstance<>(packetTransformer);
	}

	public void setCallBack(InitCallBack<T> callback) {
		this.callback = callback;
	}

	public T getTransformer(NetworkListenerState state, int packetId) throws InstantiationException, IllegalAccessException {
		LazyNewInstance<T> transformer = registry[toKey(state, packetId)];
		if (transformer == null) {
			throw new NoSuchElementException("No transformer found for state " + state + " and packet id " + packetId);
		}
		T object = transformer.getInstance();
		if (callback != null) {
			callback.onInit(object);
		}
		return object;
	}

	private static class LazyNewInstance<T> {
		private final Class<? extends T> clazz;
		public LazyNewInstance(Class<? extends T> clazz) {
			this.clazz = clazz;
		}

		private T instance;
		public T getInstance() throws InstantiationException, IllegalAccessException {
			if (instance == null) {
				instance = clazz.newInstance();
			}
			return instance;
		}
	}

	static int toKey(NetworkListenerState protocol, int packetId) {
		return (protocol.ordinal() << 8) | packetId;
	}

	@FunctionalInterface
	public static interface InitCallBack<T> {
		public void onInit(T object);
	}

}
