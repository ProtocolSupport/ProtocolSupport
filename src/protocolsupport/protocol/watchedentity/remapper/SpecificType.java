package protocolsupport.protocol.watchedentity.remapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolVersion;

public enum SpecificType {

	NONE(EType.NONE, -1);

	private static final SpecificType[] OBJECT_BY_TYPE_ID = new SpecificType[256];
	private static final SpecificType[] MOB_BY_TYPE_ID = new SpecificType[256];

	static {
		Arrays.fill(OBJECT_BY_TYPE_ID, SpecificType.NONE);
		Arrays.fill(MOB_BY_TYPE_ID, SpecificType.NONE);
		for (SpecificType type : values()) {
			switch (type.etype) {
				case OBJECT: {
					OBJECT_BY_TYPE_ID[type.typeId] = type;
					break;
				}
				case MOB: {
					MOB_BY_TYPE_ID[type.typeId] = type;
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	protected static SpecificType getObjectByTypeId(int objectTypeId) {
		return OBJECT_BY_TYPE_ID[objectTypeId];
	}

	protected static SpecificType getMobByTypeId(int mobTypeId) {
		return MOB_BY_TYPE_ID[mobTypeId];
	}

	private EType etype;
	private int typeId;
	private EnumMap<ProtocolVersion, ArrayList<RemappingEntry>> entries = new EnumMap<ProtocolVersion, ArrayList<RemappingEntry>>(ProtocolVersion.class);
	{
		for (ProtocolVersion version : ProtocolVersion.values()) {
			entries.put(version, new ArrayList<RemappingEntry>());
		}
	}

	@SuppressWarnings("deprecation")
	SpecificType(EType etype, EntityType type, RemappingEntriesForProtocol... entries) {
		this(etype, type.getTypeId(), entries);
	}

	SpecificType(EType etype, int typeId, RemappingEntriesForProtocol... entries) {
		this.etype = etype;
		this.typeId = typeId;
		for (RemappingEntriesForProtocol rp : entries) {
			this.entries.get(rp.version).addAll(Arrays.asList(rp.entries));
		}
	}

	@SuppressWarnings("deprecation")
	SpecificType(EType etype, EntityType type, SpecificType superType, RemappingEntriesForProtocol... entries) {
		this(etype, type.getTypeId(), superType, entries);
	}

	SpecificType(EType etype, int typeId, SpecificType superType, RemappingEntriesForProtocol... entries) {
		this.etype = etype;
		this.typeId = typeId;
		for (Entry<ProtocolVersion, ArrayList<RemappingEntry>> entry : superType.entries.entrySet()) {
			this.entries.get(entry.getKey()).addAll(entry.getValue());
		}
		for (RemappingEntriesForProtocol rp : entries) {
			this.entries.get(rp.version).addAll(Arrays.asList(rp.entries));
		}
	}

	public List<RemappingEntry> getRemaps(ProtocolVersion version) {
		return entries.get(version);
	}

	private enum EType {
		NONE, OBJECT, MOB
	}

	private static class RemappingEntriesForProtocol {
		private ProtocolVersion version;
		private RemappingEntry[] entries;
		protected RemappingEntriesForProtocol(ProtocolVersion version, RemappingEntry... entries) {
			this.version = version;
			this.entries = entries;
		}
	}

}
