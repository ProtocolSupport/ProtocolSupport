package protocolsupport.protocol.typeremapper.entity.metadata.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;

public class EntityMetadataRemapper {

	public static final EntityMetadataRemapper NOOP = new EntityMetadataRemapper();

	protected final EnumMap<ProtocolVersion, List<NetworkEntityMetadataObjectRemapper>> entries = new EnumMap<>(ProtocolVersion.class);

	public Map<ProtocolVersion, List<NetworkEntityMetadataObjectRemapper>> getRemaps() {
		return entries;
	}

	public List<NetworkEntityMetadataObjectRemapper> getRemaps(ProtocolVersion version) {
		return entries.computeIfAbsent(version, k -> new ArrayList<>());
	}

	public void addRemap(NetworkEntityMetadataObjectRemapper objectremapper, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getRemaps(version).add(objectremapper));
	}

	public void addRemapPerVersion(Function<ProtocolVersion, NetworkEntityMetadataObjectRemapper> objectRemapperProvider, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getRemaps(version).add(objectRemapperProvider.apply(version)));
	}

	@Override
	public EntityMetadataRemapper clone() {
		EntityMetadataRemapper clone = new EntityMetadataRemapper();
		entries.forEach((version, lEntries) -> clone.getRemaps(version).addAll(lEntries));
		return clone;
	}

}
