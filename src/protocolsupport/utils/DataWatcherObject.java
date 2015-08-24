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
		BYTE, SHORT, INT, FLOAT, STRING, ITEMSTACK, VECTOR3I, VECTOR3F;

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
			return ordinal();
		}

		public static ValueType fromId(ProtocolVersion version, int id) {
			return values()[id];//BY_PROTOCOL_AND_ID.get(version)[id];
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