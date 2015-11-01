package protocolsupport.utils;

import java.util.ArrayList;
import java.util.Arrays;

import protocolsupport.api.ProtocolVersion;

public class ProtocolVersionsHelper {

	public static final ProtocolVersion[] ALL = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_5_2, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_8
	};

	public static final ProtocolVersion[] BEFORE_1_9 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_5_2, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_8
	};

	public static final ProtocolVersion[] BEFORE_1_8 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_5_2, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10, 
	};

	public static final ProtocolVersion[] BEFORE_1_7 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_5_2, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_6_4
	};

	public static final ProtocolVersion[] BEFORE_1_6 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_1_5_2
	};

	public static final ProtocolVersion[] BEFORE_MINECRAFT_PE_0_13 = new ProtocolVersion[] {
		ProtocolVersion.MINECRAFT_PE
	};

	public static final ProtocolVersion[] concat(ProtocolVersion[] versions1, ProtocolVersion... versions2) {
		ArrayList<ProtocolVersion> list = new ArrayList<>();
		list.addAll(Arrays.asList(versions1));
		list.addAll(Arrays.asList(versions2));
		return list.toArray(new ProtocolVersion[list.size()]);
	}

}
