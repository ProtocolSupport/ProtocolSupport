package protocolsupport.protocol.utils;

import java.util.Arrays;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ProtocolVersionsHelper {

	public static final ProtocolVersion LATEST_PC = ProtocolVersion.getLatest(ProtocolType.PC);

	public static final ProtocolVersion[] UP_1_6 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] UP_1_8 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] UP_1_9 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_9);

	public static final ProtocolVersion[] UP_1_11 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_11);

	public static final ProtocolVersion[] UP_1_12 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_12);

	public static final ProtocolVersion[] UP_1_13 = ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_13);

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

	public static final ProtocolVersion[] BEFORE_1_13 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_13);

	public static final ProtocolVersion[] BEFORE_1_13_1 = ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_13_1);

	public static final ProtocolVersion[] ALL_PC = ProtocolVersion.getAllBetween(ProtocolVersion.getOldest(ProtocolType.PC), LATEST_PC);

	public static final ProtocolVersion[] ALL_1_12 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] ALL_1_9 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_9);

	public static final ProtocolVersion[] RANGE__1_12__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_11_1__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_11_1__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_11__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_11__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_9__1_10 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_10);

	public static final ProtocolVersion[] RANGE__1_9__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_9__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_8__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_7_5__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_10__1_12_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_12_2);

	public static final ProtocolVersion[] RANGE__1_10__1_13_2 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_13_2);

	public static final ProtocolVersion[] RANGE__1_6__1_8 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] RANGE__1_6__1_7 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1);

	public static final ProtocolVersion[] RANGE__1_7_5__1_8 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] RANGE__1_8__1_9 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_8);

	public static final ProtocolVersion[] RANGE__1_6__1_10 = ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_10);

	protected static final Int2ObjectOpenHashMap<ProtocolVersion> byOldProtocolId = new Int2ObjectOpenHashMap<>();
	protected static final Int2ObjectOpenHashMap<ProtocolVersion> byNewProtocolId = new Int2ObjectOpenHashMap<>();


	static {
		Arrays.stream(ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_6_4)).forEach(version -> byOldProtocolId.put(version.getId(), version));
		Arrays.stream(ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_7_5)).forEach(version -> byNewProtocolId.put(version.getId(), version));
	}

	public static ProtocolVersion getOldProtocolVersion(int protocolid) {
		ProtocolVersion version = byOldProtocolId.get(protocolid);
		return version != null ? version : ProtocolVersion.MINECRAFT_LEGACY;
	}

	public static ProtocolVersion getNewProtocolVersion(int protocolid) {
		ProtocolVersion version = byNewProtocolId.get(protocolid);
		return version != null ? version : ProtocolVersion.MINECRAFT_FUTURE;
	}

}
