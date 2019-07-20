package protocolsupport.protocol.typeremapper.entity.metadata.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;

public class EntityMetadataRemapper {

	public static final EntityMetadataRemapper NOOP = new EntityMetadataRemapper();

	protected final EnumMap<ProtocolVersion, NetworkEntityMetadataObjectRemapperList> entries = new EnumMap<>(ProtocolVersion.class);

	public List<NetworkEntityMetadataObjectRemapper> getRemaps(ProtocolVersion version) {
		return getRemapsOrCreate(version).getCombined();
	}

	protected NetworkEntityMetadataObjectRemapperList getRemapsOrCreate(ProtocolVersion version) {
		return entries.computeIfAbsent(version, k -> new NetworkEntityMetadataObjectRemapperList());
	}

	protected void addRemap(NetworkEntityMetadataObjectRemapper objectremapper, ProtocolVersion... versions) {
		addRemapPerVersion(version -> objectremapper, versions);
	}

	protected void addRemapPerVersion(Function<ProtocolVersion, NetworkEntityMetadataObjectRemapper> objectRemapperProvider, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getRemapsOrCreate(version).normal.add(objectRemapperProvider.apply(version)));
	}

	protected void addPostRemap(NetworkEntityMetadataObjectRemapper objectremapper, ProtocolVersion... versions) {
		addPostRemapPerVersion(version -> objectremapper, versions);
	}

	protected void addPostRemapPerVersion(Function<ProtocolVersion, NetworkEntityMetadataObjectRemapper> objectRemapperProvider, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getRemapsOrCreate(version).post.add(objectRemapperProvider.apply(version)));
	}

	protected static class NetworkEntityMetadataObjectRemapperList {
		protected final List<NetworkEntityMetadataObjectRemapper> normal = new ArrayList<>();
		protected final List<NetworkEntityMetadataObjectRemapper> post = new ArrayList<>();
		public List<NetworkEntityMetadataObjectRemapper> getCombined() {
			List<NetworkEntityMetadataObjectRemapper> combined = new ArrayList<>();
			combined.addAll(normal);
			combined.addAll(post);
			return combined;
		}
	}

}
