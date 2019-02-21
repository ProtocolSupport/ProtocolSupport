package protocolsupport.protocol.typeremapper.basic;

import protocolsupport.api.ProtocolVersion;

public class WindowSlotsRemappingHelper {

	public static final int PLAYER_OFF_HAND_SLOT = 45;
	public static final int BREWING_BLAZE_POWDER_SLOT = 4;
	public static final int ENCHANT_LAPIS_SLOT = 1;

	public static boolean hasPlayerOffhandSlot(ProtocolVersion version) {
		return version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_9);
	}

	public static boolean hasBrewingBlazePowderSlot(ProtocolVersion version) {
		return version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_9);
	}

	public static boolean hasEnchantLapisSlot(ProtocolVersion version) {
		return version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
