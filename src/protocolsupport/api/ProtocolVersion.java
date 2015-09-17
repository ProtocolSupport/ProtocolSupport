package protocolsupport.api;

public enum ProtocolVersion {

	MINECRAFT_1_8(47, "1.8"),
	MINECRAFT_1_7_10(5, "1.7.10"),
	MINECRAFT_1_7_5(4, "1.7.5"),
	MINECRAFT_1_6_4(78, "1.6.4"),
	MINECRAFT_1_6_2(74, "1.6.2"),
	MINECRAFT_1_5_2(61, "1.5.2"),
	MINECRAFT_PE(-2, "PE"),
	UNKNOWN(-1),
	NOT_SET(0);

	private int id;
	private String name;

	ProtocolVersion(int id) {
		this.id = id;
	}

	ProtocolVersion(int id, String name) {
		this(id);
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

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
			case 61: {
				return MINECRAFT_1_5_2;
			}
			case -2: {
				return MINECRAFT_PE;
			}
		}
		return UNKNOWN;
	}

}