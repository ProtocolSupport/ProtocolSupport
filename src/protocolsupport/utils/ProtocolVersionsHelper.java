package protocolsupport.utils;

import protocolsupport.api.ProtocolVersion;

public class ProtocolVersionsHelper {

	public static final ProtocolVersion[] ALL = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_5_2,
		ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4,
		ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10,
		ProtocolVersion.MINECRAFT_1_8
	};

	public static final ProtocolVersion[] BEFORE_1_9 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_5_2,
		ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4,
		ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10,
		ProtocolVersion.MINECRAFT_1_8
	};

	public static final ProtocolVersion[] BEFORE_1_8 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_5_2,
		ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4,
		ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10, 
	};

	public static final ProtocolVersion[] BEFORE_1_7 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_5_2,
		ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4
	};

	public static final ProtocolVersion[] BEFORE_1_6 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_5_2
	};

	public static final ProtocolVersion[] BEFORE_1_5 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_4_7
	};

}
