package protocolsupport.protocol.typeremapper.entity.metadata.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;

public class EntityMetadataRemapper {

	public static final EntityMetadataRemapper NOOP = new EntityMetadataRemapper();

	protected final EnumMap<ProtocolVersion, List<DataWatcherObjectRemapper>> entries = new EnumMap<>(ProtocolVersion.class);

	public Map<ProtocolVersion, List<DataWatcherObjectRemapper>> getRemaps() {
		return entries;
	}

	public List<DataWatcherObjectRemapper> getRemaps(ProtocolVersion version) {
		return entries.computeIfAbsent(version, k -> new ArrayList<>());
	}

	public void addRemap(DataWatcherObjectRemapper objectremapper, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getRemaps(version).add(objectremapper));
	}

	public void addRemapPerVersion(Function<ProtocolVersion, DataWatcherObjectRemapper> objectRemapperProvider, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getRemaps(version).add(objectRemapperProvider.apply(version)));
	}

	@Override
	public EntityMetadataRemapper clone() {
		EntityMetadataRemapper clone = new EntityMetadataRemapper();
		entries.forEach((version, lEntries) -> clone.getRemaps(version).addAll(lEntries));
		return clone;
	}

}
