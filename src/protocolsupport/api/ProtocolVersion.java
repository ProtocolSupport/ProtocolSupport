package protocolsupport.api;

public enum ProtocolVersion {

	MINECRAFT_1_8(47, 7, "1.8"),
	MINECRAFT_1_7_10(5, 6, "1.7.10"),
	MINECRAFT_1_7_5(4, 5, "1.7.5"),
	MINECRAFT_1_6_4(78, 4, "1.6.4"),
	MINECRAFT_1_6_2(74, 3, "1.6.2"),
	MINECRAFT_1_6_1(73, 2, "1.6.1"),
	MINECRAFT_1_5_2(61, 1, "1.5.2"),
	MINECRAFT_1_4_7(51, 0, "1.4.7"),
	UNKNOWN(-1),
	NOT_SET(0);

	private int id;
	private int orderId;
	private String name;

	ProtocolVersion(int id) {
		this.id = id;
		this.orderId = -1;
	}

	ProtocolVersion(int id, int orderId, String name) {
		this(id);
		this.orderId = orderId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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
		return orderId >= startId && orderId <= endId;
	}

	private static final ProtocolVersion[] byOrderId = new ProtocolVersion[ProtocolVersion.values().length - 2];
	static {
		for (ProtocolVersion version : ProtocolVersion.values()) {
			if (version.orderId != -1) {
				byOrderId[version.orderId] = version;
			}
		}
	}

	@Deprecated
	public static ProtocolVersion fromId(int id) {
		switch (id) {
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
			case 51: {
				return MINECRAFT_1_4_7;
			}
		}
		return UNKNOWN;
	}

	public static ProtocolVersion[] getAllBetween(ProtocolVersion start, ProtocolVersion end) {
		int startId = Math.min(start.orderId, end.orderId);
		int endId = Math.max(start.orderId, end.orderId);
		ProtocolVersion[] between = new ProtocolVersion[endId - startId + 1];
		for (int i = startId; i <= endId; i++) {
			between[i - startId] = byOrderId[i]; 
		}
		return between;
	}

	public static ProtocolVersion getLatest() {
		return ProtocolVersion.MINECRAFT_1_8;
	}

}