package protocolsupport.protocol.transformer.utils.registry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

import net.minecraft.server.v1_8_R3.EnumProtocol;

import protocolsupport.utils.Utils;

public class MiddleTransformerRegistry<T> {

	private final EnumMap<EnumProtocol, Constructor<? extends T>[]> registry = new EnumMap<>(EnumProtocol.class);

	@SuppressWarnings("unchecked")
	public void register(EnumProtocol protocol, int packetId, Class<? extends T> packetTransformer) throws NoSuchMethodException, SecurityException {
		if (!registry.containsKey(protocol)) {
			registry.put(protocol, new Constructor[256]);
		}
		registry.get(protocol)[packetId] = Utils.setAccessible(packetTransformer.getConstructor());
	}

	public T getTransformer(EnumProtocol protocol, int packetId) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends T>[] transformers = registry.get(protocol);
		if (transformers == null) {
			return null;
		}
		Constructor<? extends T> constructor = transformers[packetId];
		if (constructor == null) {
			return null;
		}
		return constructor.newInstance();
	}

}
