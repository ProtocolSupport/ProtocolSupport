package protocolsupport.protocol.typeremapper.legacy;

import java.util.regex.Pattern;

import protocolsupport.protocol.utils.NamespacedKeyUtils;

public class LegacyCustomPayloadChannelName {

	public static final String MODERN_REGISTER = "minecraft:register";
	public static final String MODERN_UNREGISTER = "minecraft:unregister";
	public static final String MODERN_TRADER_LIST = "minecraft:trader_list";
	public static final String MODERN_BRAND = "minecraft:brand";

	public static final String LEGACY_REGISTER = "REGISTER";
	public static final String LEGACY_UNREGISTER = "UNREGISTER";
	public static final String LEGACY_COMMAND_BLOCK_NAME = "MC|AutoCmd";
	public static final String LEGACY_COMMAND_RIGHT_NAME = "MC|AdvCmd";
	public static final String LEGACY_COMMAND_TYPO_NAME = "MC|AdvCdm";
	public static final String LEGACY_BOOK_EDIT = "MC|BEdit";
	public static final String LEGACY_BOOK_SIGN = "MC|BSign";
	public static final String LEGACY_SET_BEACON = "MC|Beacon";
	public static final String LEGACY_NAME_ITEM = "MC|ItemName";
	public static final String LEGACY_PICK_ITEM = "MC|PickItem";

	public static String toPre13(String modernName) {
		switch (modernName) {
			case MODERN_TRADER_LIST: {
				return "MC|TrList";
			}
			case MODERN_BRAND: {
				return "MC|Brand";
			}
			case "minecraft:book_open": {
				return "MC|BOpen";
			}
			case MODERN_REGISTER: {
				return LEGACY_REGISTER;
			}
			case MODERN_UNREGISTER: {
				return LEGACY_UNREGISTER;
			}
			default: {
				return modernName;
			}
		}
	}

	protected static final Pattern invalid_pattern = Pattern.compile("[^a-z0-9._-]+");
	public static String fromPre13(String legacyName) {
		switch (legacyName) {
			case LEGACY_REGISTER: {
				return MODERN_REGISTER;
			}
			case LEGACY_UNREGISTER: {
				return MODERN_UNREGISTER;
			}
			case "MC|Brand": {
				return MODERN_BRAND;
			}
			case "BungeeCord": {
				return "bungeecord:main";
			}
			default: {
				String[] split = legacyName.toLowerCase().split(NamespacedKeyUtils.SEPARATOR, 2);
				if (split.length == 1) {
					return NamespacedKeyUtils.combine("l", invalid_pattern.matcher(split[0]).replaceAll(""));
				} else {
					return NamespacedKeyUtils.combine(
						invalid_pattern.matcher(split[0]).replaceAll(""),
						invalid_pattern.matcher(split[1]).replaceAll("")
					);
				}
			}
		}
	}

}
