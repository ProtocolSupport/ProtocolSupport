package protocolsupport.protocol.typeremapper.entity.format.metadata.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;

public class NetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry> {

	public static final NetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry> NOOP = new NetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.INSTANCE);

	protected final R registry;
	protected final EnumMap<ProtocolVersion, NetworkEntityMetadataObjectTransformerList> entries = new EnumMap<>(ProtocolVersion.class);

	protected NetworkEntityMetadataFormatTransformerFactory(R registry) {
		this.registry = registry;
	}

	public List<NetworkEntityMetadataFormatTransformer> get(ProtocolVersion version) {
		return getOrCreate(version).getCombined();
	}

	protected NetworkEntityMetadataObjectTransformerList getOrCreate(ProtocolVersion version) {
		return entries.computeIfAbsent(version, k -> new NetworkEntityMetadataObjectTransformerList());
	}

	protected void add(NetworkEntityMetadataFormatTransformer transformer, ProtocolVersion... versions) {
		add(version -> transformer, versions);
	}

	protected void add(Function<ProtocolVersion, NetworkEntityMetadataFormatTransformer> transformerSupplier, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getOrCreate(version).normal.add(transformerSupplier.apply(version)));
	}

	protected void addPost(NetworkEntityMetadataFormatTransformer transformer, ProtocolVersion... versions) {
		addPost(version -> transformer, versions);
	}

	protected void addPost(Function<ProtocolVersion, NetworkEntityMetadataFormatTransformer> transformerSupplier, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getOrCreate(version).post.add(transformerSupplier.apply(version)));
	}

	protected static class NetworkEntityMetadataObjectTransformerList {

		protected final List<NetworkEntityMetadataFormatTransformer> normal = new ArrayList<>();
		protected final List<NetworkEntityMetadataFormatTransformer> post = new ArrayList<>();

		public List<NetworkEntityMetadataFormatTransformer> getCombined() {
			List<NetworkEntityMetadataFormatTransformer> combined = new ArrayList<>(normal);
			combined.addAll(post);
			return combined;
		}

	}

}
