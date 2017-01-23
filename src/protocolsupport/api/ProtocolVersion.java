package protocolsupport.api;

import gnu.trove.map.hash.TIntObjectHashMap;

public enum ProtocolVersion {

	MINECRAFT_FUTURE(-1, 17),
	MINECRAFT_1_11_1(316, 16, "1.11.1"),
	MINECRAFT_1_11(315, 15, "1.11"),
	MINECRAFT_1_10(210, 14, "1.10"),
	MINECRAFT_1_9_4(110, 13, "1.9.4"),
	MINECRAFT_1_9_2(109, 12, "1.9.2"),
	MINECRAFT_1_9_1(108, 11, "1.9.1"),
	MINECRAFT_1_9(107, 10, "1.9"),
	MINECRAFT_1_8(47, 9, "1.8"),
	MINECRAFT_1_7_10(5, 8, "1.7.10"),
	MINECRAFT_1_7_5(4, 7, "1.7.5"),
	MINECRAFT_1_6_4(78, 6, "1.6.4"),
	MINECRAFT_1_6_2(74, 5, "1.6.2"),
	MINECRAFT_1_6_1(73, 4, "1.6.1"),
	MINECRAFT_1_5_2(61, 3, "1.5.2"),
	MINECRAFT_1_5_1(60, 2, "1.5.1"),
	MINECRAFT_1_4_7(51, 1, "1.4.7"),
	MINECRAFT_LEGACY(-1, 0),
	MINECRAFT_PE(-2, -1, "PE"),
	UNKNOWN(-1);

	private final int id;
	private final int orderId;
	private final String name;

	ProtocolVersion(int id) {
		this(id, -1);
	}

	ProtocolVersion(int id, int orderId) {
		this(id, orderId, null);
	}

	ProtocolVersion(int id, int orderId, String name) {
		this.id = id;
		this.orderId = orderId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isSupported() {
		return name != null;
	}

	public boolean isAfter(ProtocolVersion another) {
		return orderId > another.orderId;
	}

	public boolean isAfterOrEq(ProtocolVersion another) {
		return orderId >= another.orderId;
	}

	public boolean isBefore(ProtocolVersion another) {
		return orderId < another.orderId;
	}

	public boolean isBeforeOrEq(ProtocolVersion another) {
		return orderId <= another.orderId;
	}

	public boolean isBetween(ProtocolVersion start, ProtocolVersion end) {
		int startId = Math.min(start.orderId, end.orderId);
		int endId = Math.max(start.orderId, end.orderId);
		return (orderId >= startId) && (orderId <= endId);
	}

	private static final TIntObjectHashMap<ProtocolVersion> byProtocolId = new TIntObjectHashMap<>();
	static {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			if (version.id != -1) {
				byProtocolId.put(version.id, version);
			}
		}
	}

	@Deprecated
	public static ProtocolVersion fromId(int id) {
		ProtocolVersion version = byProtocolId.get(id);
		return version != null ? version : UNKNOWN;
	}

	private static final ProtocolVersion[] byOrderId = new ProtocolVersion[ProtocolVersion.values().length - 1];
	static {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			if (version.orderId != -1) {
				byOrderId[version.orderId] = version;
			}
			case 109: {
				return MINECRAFT_1_9_2;
			}
			case 108: {
				return MINECRAFT_1_9_1;
			}
			case 107: {
				return MINECRAFT_1_9;
			}
			case 47: {
				return MINECRAFT_1_8;
			}
			case 5: {
				return MINECRAFT_1_7_10;
			}
			case 4: {
				return MINECRAFT_1_7_5;
			}
			case 78: {
				return MINECRAFT_1_6_4;
			}
			case 74: {
				return MINECRAFT_1_6_2;
			}
			case 73: {
				return MINECRAFT_1_6_1;
			}
			case 61: {
				return MINECRAFT_1_5_2;
			}
			case -2: {
				return MINECRAFT_PE;
			}
		}
	}

	public static ProtocolVersion[] getAllBetween(ProtocolVersion start, ProtocolVersion end) {
		int startId = Math.min(start.orderId, end.orderId);
		int endId = Math.max(start.orderId, end.orderId);
		ProtocolVersion[] between = new ProtocolVersion[(endId - startId) + 1];
		for (int i = startId; i <= endId; i++) {
			between[i - startId] = byOrderId[i];
		}
		return between;
	}

	public static ProtocolVersion[] getAllBefore(ProtocolVersion version) {
		return getAllBetween(getOldest(), version);
	}

	public static ProtocolVersion[] getAllAfter(ProtocolVersion version) {
		return getAllBetween(version, getLatest());
	}

	public static ProtocolVersion getLatest() {
		return ProtocolVersion.MINECRAFT_1_11_1;
	}

	public static ProtocolVersion getOldest() {
		return ProtocolVersion.MINECRAFT_1_4_7;
	}

}