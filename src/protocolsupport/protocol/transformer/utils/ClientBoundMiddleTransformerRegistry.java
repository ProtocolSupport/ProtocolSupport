package protocolsupport.protocol.transformer.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.utils.Utils;

public class ClientBoundMiddleTransformerRegistry<T> {

	private final EnumMap<EnumProtocol, Constructor<? extends ClientBoundMiddlePacket<T>>[]> registry = new EnumMap<>(EnumProtocol.class);

	@SuppressWarnings("unchecked")
	public void register(EnumProtocol protocol, int packetId, Class<? extends ClientBoundMiddlePacket<T>> packetTransformer) throws NoSuchMethodException, SecurityException {
		if (!registry.containsKey(protocol)) {
			registry.put(protocol, new Constructor[256]);
		}
		registry.get(protocol)[packetId] = Utils.setAccessible(packetTransformer.getConstructor());
	}

	public ClientBoundMiddlePacket<T> getTransformer(EnumProtocol protocol, int packetId) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends ClientBoundMiddlePacket<T>>[] transformers = registry.get(protocol);
		if (transformers == null) {
			return null;
		}
		Constructor<? extends ClientBoundMiddlePacket<T>> constructor = transformers[packetId];
		if (constructor == null) {
			return null;
		}
		return constructor.newInstance();
	}

}
