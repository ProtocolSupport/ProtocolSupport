package protocolsupport.protocol.transformer.utils.registry;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

import net.minecraft.server.v1_8_R3.EnumProtocol;

public class MiddleTransformerRegistry<T> {

	private final EnumMap<EnumProtocol, Object[]> registry = new EnumMap<>(EnumProtocol.class);

	public void register(EnumProtocol protocol, int packetId, Class<? extends T> packetTransformer) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		if (!registry.containsKey(protocol)) {
			registry.put(protocol, new Object[256]);
		}
		registry.get(protocol)[packetId] = packetTransformer.newInstance();
	}

	@SuppressWarnings("unchecked")
	public T getTransformer(EnumProtocol protocol, int packetId) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object[] transformers = registry.get(protocol);
		if (transformers == null) {
			return null;
		}
		return (T) transformers[packetId];
	}

}
