package protocolsupport.protocol.utils;

import java.util.ArrayList;
import java.util.Arrays;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

public class ProtocolVersionsHelper {

	public static final ProtocolVersion LATEST_PC = ProtocolVersion.getLatest(ProtocolType.PC);

	public static final ProtocolVersion[] BEFORE_1_5 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_5_1);

	public static final ProtocolVersion[] BEFORE_1_6 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] BEFORE_1_7 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_7_5);

	public static final ProtocolVersion[] BEFORE_1_8 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] BEFORE_1_9 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_9);

	public static final ProtocolVersion[] BEFORE_1_9_1 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_9_1);

	public static final ProtocolVersion[] BEFORE_1_10 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_10);

	public static final ProtocolVersion[] BEFORE_1_11 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_11);

	public static final ProtocolVersion[] BEFORE_1_11_1 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_11_1);

	public static final ProtocolVersion[] BEFORE_1_12 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_12);

	public static final ProtocolVersion[] ALL_PC = ProtocolVersion.getAllBetween(ProtocolVersion.getOldest(ProtocolType.PC), LATEST_PC);

	public static final ProtocolVersion[] ALL_1_9 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_9);

	public static final ProtocolVersion[] RANGE__1_11_1__1_12 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.MINECRAFT_1_12);

	public static final ProtocolVersion[] RANGE__1_11__1_12 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.MINECRAFT_1_12);

	public static final ProtocolVersion[] RANGE__1_9__1_12 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_12);

	public static final ProtocolVersion[] RANGE__1_10__1_12 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_12);

	public static final ProtocolVersion[] RANGE__1_6__1_7 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] RANGE__1_7_5__1_8 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] RANGE__1_8__1_9 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] RANGE__1_6__1_10 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_10);

	public static final ProtocolVersion[] AFTER_1_11 = ProtocolVersion.getAllAfterE(ProtocolVersion.MINECRAFT_1_11_1);

	public static final ProtocolVersion[] AFTER_1_8 = ProtocolVersion.getAllAfterE(ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] concat(ProtocolVersion[] versions, ProtocolVersion... moreVersions) {
		ArrayList<ProtocolVersion> all = new ArrayList<>();
		all.addAll(Arrays.asList(versions));
		all.addAll(Arrays.asList(moreVersions));
		return all.toArray(new ProtocolVersion[all.size()]);
	}

}
