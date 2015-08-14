package protocolsupport.api;

public enum ProtocolVersion {

	MINECRAFT_1_8(47),
	MINECRAFT_1_7_10(5),
	MINECRAFT_1_7_5(4),
	MINECRAFT_1_6_4(78),
	MINECRAFT_1_6_2(74),
	MINECRAFT_1_5_2(61),
	MINECRAFT_PE(-2),
	UNKNOWN(-1),
	NOT_SET(0);

	private int id;

	ProtocolVersion(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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