package protocolsupport.protocol;

import java.util.UUID;

public enum ProtocolVersion {

	MINECRAFT_1_8(47),
	MINECRAFT_1_7_10(5),
	MINECRAFT_1_7_5(4),
	MINECRAFT_1_6_4(78),
	MINECRAFT_1_6_2(74),
	MINECRAFT_1_5_2(61),
	UNKNOWN(-1);

	public static final UUID MINECRAFT_1_7_10_UUID_ID = UUID.fromString("00000000-0000-1000-0000-000000000005");
	public static final UUID MINECRAFT_1_7_5_UUID_ID = UUID.fromString("00000000-0000-1000-0000-000000000004");
	public static final UUID MINECRAFT_1_6_4_UUID_ID = UUID.fromString("00000000-0000-1000-0000-000000000078");
	public static final UUID MINECRAFT_1_6_2_UUID_ID = UUID.fromString("00000000-0000-1000-0000-000000000074");
	public static final UUID MINECRAFT_1_5_2_UUID_ID = UUID.fromString("00000000-0000-1000-0000-000000000061");

	private int id;

	ProtocolVersion(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public UUID getUUID() {
		switch (id) {
			case 5: {
				return MINECRAFT_1_7_10_UUID_ID;
			}
			case 4: {
				return MINECRAFT_1_7_5_UUID_ID;
			}
			case 78: {
				return MINECRAFT_1_6_4_UUID_ID;
			}
			case 74: {
				return MINECRAFT_1_6_2_UUID_ID;
			}
			case 61: {
				return MINECRAFT_1_5_2_UUID_ID;
			}
		}
		throw new RuntimeException("Can't get uuid for version "+this);
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
		}
		return UNKNOWN;
	}

}