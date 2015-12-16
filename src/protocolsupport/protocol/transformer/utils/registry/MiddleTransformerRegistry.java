package protocolsupport.protocol.transformer.utils.registry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupport.utils.Utils;

public class MiddleTransformerRegistry<T> {

	private final ArrayList<ProtocolVersion> required = new ArrayList<ProtocolVersion>();
	private final EnumMap<EnumProtocol, Constructor<? extends T>[]> registry = new EnumMap<>(EnumProtocol.class);

	public MiddleTransformerRegistry(ProtocolVersion... versions) {
		if (versions.length == 0) {
			throw new IllegalArgumentException("Supported versions can't be empty");
		}
		for (ProtocolVersion version : versions) {
			required.add(version);
		}
	}

	@SuppressWarnings("unchecked")
	public void register(EnumProtocol protocol, int packetId, Class<? extends T> packetTransformer) throws NoSuchMethodException, SecurityException {
		try {
			SupportedVersions transformerversions = packetTransformer.getAnnotation(SupportedVersions.class);
			EnumSet<ProtocolVersion> supported = EnumSet.copyOf(Arrays.asList(transformerversions.value()));
			if (!supported.containsAll(required)) {
				throw new IllegalArgumentException(
					"Transformer class "+
					packetTransformer.getName()+
					" doesn't support required versions: "+
					new ArrayList<ProtocolVersion>(required).removeAll(supported)
				);
			}
		} catch (NullPointerException ex) {
			throw new IllegalArgumentException("Transformer class "+packetTransformer.getName()+" should be annotated with SupportedVersions annotation");
		}
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
