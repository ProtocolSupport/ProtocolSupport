package protocolsupport.protocol.transformer.utils.registry;

import net.minecraft.server.v1_8_R3.EnumProtocol;

@SuppressWarnings("unchecked")
public class MiddleTransformerRegistry<T> {

	private static final int enumProtocolLength = EnumProtocol.values().length;

	private final LazyNewInstance<T>[] registry = new LazyNewInstance[enumProtocolLength * 256];

	public void register(EnumProtocol protocol, int packetId, Class<? extends T> packetTransformer) {
		registry[toKey(protocol, packetId)] = new LazyNewInstance<T>(packetTransformer);
	}

	public T getTransformer(EnumProtocol protocol, int packetId) throws InstantiationException, IllegalAccessException {
		LazyNewInstance<T> transformer = registry[toKey(protocol, packetId)];
		if (transformer == null) {
			return null;
		}
		return transformer.getInstance();
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

	static int toKey(EnumProtocol protocol, int packetId) {
		return (protocol.ordinal() << 8) | packetId;
	}

}
