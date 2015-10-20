package protocolsupport.utils;

import java.util.EnumMap;

import protocolsupport.api.ProtocolVersion;

public class DataWatcherObject {

	public DataWatcherObject.ValueType type;
	public Object value;

	public DataWatcherObject(DataWatcherObject.ValueType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public void toByte() {
		type = ValueType.BYTE;
		value = ((Number) value).byteValue();
	}

	public void toShort() {
		type = ValueType.SHORT;
		value = ((Number) value).shortValue();
	}

	public void toInt() {
		type = ValueType.INT;
		value = ((Number) value).intValue();
	}

	public void toFloat() {
		type = ValueType.FLOAT;
		value = ((Number) value).floatValue();
	}

	@Override
	public String toString() {
		return
			new StringBuilder()
			.append("type: ").append(type).append(" ")
			.append("value: ").append(value)
			.toString();
	}

	public static enum ValueType {
		BYTE(new TypeToProtocolsMappingEntry(0, ProtocolVersionsHelper.ALL)),
		SHORT(new TypeToProtocolsMappingEntry(1, ProtocolVersionsHelper.ALL)),
		INT(new TypeToProtocolsMappingEntry(2, ProtocolVersionsHelper.ALL)),
		FLOAT(new TypeToProtocolsMappingEntry(3, ProtocolVersionsHelper.ALL)),
		STRING(new TypeToProtocolsMappingEntry(4, ProtocolVersionsHelper.ALL)),
		ITEMSTACK(new TypeToProtocolsMappingEntry(5, ProtocolVersionsHelper.ALL)),
		VECTOR3I(new TypeToProtocolsMappingEntry(6, ProtocolVersionsHelper.BEFORE_1_9)),
		VECTOR3F(new TypeToProtocolsMappingEntry(7, ProtocolVersion.MINECRAFT_1_8));

		private static final EnumMap<ProtocolVersion, ValueType[]> TYPE_BY_PROTOCOL_AND_ID = new EnumMap<ProtocolVersion, ValueType[]>(ProtocolVersion.class);

		static {
			for (ProtocolVersion version : ProtocolVersion.values()) {
				ValueType[] byId = new ValueType[256];
				for (ValueType vtype : ValueType.values()) {
					if (vtype.types.containsKey(version)) {
						byId[vtype.types.get(version)] = vtype;
					}
				}
				TYPE_BY_PROTOCOL_AND_ID.put(version, byId);
			}
		}

		private final EnumMap<ProtocolVersion, Integer> types = new EnumMap<ProtocolVersion, Integer>(ProtocolVersion.class);

		ValueType(TypeToProtocolsMappingEntry... mappings) {
			for (TypeToProtocolsMappingEntry mapping : mappings) {
				for (ProtocolVersion version : mapping.versions) {
					types.put(version, mapping.type);
				}
			}
		}

		public int getId(ProtocolVersion version) {
			Integer type = types.get(version);
			if (type == null) {
				throw new IllegalArgumentException("Type id for protocol version "+version+" doesn't exist for datawatcher valuetype "+this);
			}
			return type;
		}

		public static ValueType fromId(ProtocolVersion version, int id) {
			ValueType vtype = TYPE_BY_PROTOCOL_AND_ID.get(version)[id];
			if (vtype == null) {
				throw new IllegalArgumentException("Datawatcher valuetype doesn't exist for protocol version "+version+" and type id "+id);
			}
			return vtype;
		}

		private static final class TypeToProtocolsMappingEntry {
			private int type;
			private ProtocolVersion[] versions;
			public TypeToProtocolsMappingEntry(int type, ProtocolVersion... versions) {
				this.type = type;
				this.versions = versions;
			}
		}

	}

}