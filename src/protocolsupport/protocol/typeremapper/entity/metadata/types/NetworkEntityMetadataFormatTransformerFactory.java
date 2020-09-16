package protocolsupport.protocol.typeremapper.entity.metadata.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataFormatTransformer;

public class NetworkEntityMetadataFormatTransformerFactory {

	public static final NetworkEntityMetadataFormatTransformerFactory NOOP = new NetworkEntityMetadataFormatTransformerFactory();

	protected final EnumMap<ProtocolVersion, NetworkEntityMetadataObjectTransformerList> entries = new EnumMap<>(ProtocolVersion.class);

	public List<NetworkEntityMetadataFormatTransformer> getObjectsTransformers(ProtocolVersion version) {
		return getOrCreateObjectsTransformers(version).getCombined();
	}

	protected NetworkEntityMetadataObjectTransformerList getOrCreateObjectsTransformers(ProtocolVersion version) {
		return entries.computeIfAbsent(version, k -> new NetworkEntityMetadataObjectTransformerList());
	}

	protected void addTransformer(NetworkEntityMetadataFormatTransformer objectconverter, ProtocolVersion... versions) {
		addTransformerPerVersion(version -> objectconverter, versions);
	}

	protected void addTransformerPerVersion(Function<ProtocolVersion, NetworkEntityMetadataFormatTransformer> objectRemapperProvider, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getOrCreateObjectsTransformers(version).normal.add(objectRemapperProvider.apply(version)));
	}

	protected void addPostTransformer(NetworkEntityMetadataFormatTransformer objectremapper, ProtocolVersion... versions) {
		addPostTransformerPerVersion(version -> objectremapper, versions);
	}

	protected void addPostTransformerPerVersion(Function<ProtocolVersion, NetworkEntityMetadataFormatTransformer> objectRemapperProvider, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getOrCreateObjectsTransformers(version).post.add(objectRemapperProvider.apply(version)));
	}

	protected static class NetworkEntityMetadataObjectTransformerList {

		protected final List<NetworkEntityMetadataFormatTransformer> normal = new ArrayList<>();
		protected final List<NetworkEntityMetadataFormatTransformer> post = new ArrayList<>();

		public List<NetworkEntityMetadataFormatTransformer> getCombined() {
			List<NetworkEntityMetadataFormatTransformer> combined = new ArrayList<>();
			combined.addAll(normal);
			combined.addAll(post);
			return combined;
		}

	}

}
