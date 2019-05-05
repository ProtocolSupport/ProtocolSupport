package protocolsupport.protocol.typeremapper.legacy;

import java.util.regex.Pattern;

import protocolsupport.protocol.utils.NamespacedKeyUtils;

public class LegacyCustomPayloadChannelName {

	public static final String MODERN_REGISTER = "minecraft:register";
	public static final String MODERN_UNREGISTER = "minecraft:unregister";
	public static final String MODERN_TRADE_LIST = "minecraft:trader_list";
	public static final String MODERN_BRAND = "minecraft:brand";
	public static final String MODERN_BOOK_OPEN = "minecraft:book_open";
	public static final String MODERN_BUNGEE = "bungeecord:main";

	public static final String LEGACY_REGISTER = "REGISTER";
	public static final String LEGACY_UNREGISTER = "UNREGISTER";
	public static final String LEGACY_BRAND = "MC|Brand";
	public static final String LEGACY_BOOK_OPEN = "MC|BOpen";
	public static final String LEGACY_BUNGEE = "BungeeCord";
	public static final String LEGACY_COMMAND_BLOCK_NAME = "MC|AutoCmd";
	public static final String LEGACY_COMMAND_RIGHT_NAME = "MC|AdvCmd";
	public static final String LEGACY_COMMAND_TYPO_NAME = "MC|AdvCdm";
	public static final String LEGACY_BOOK_EDIT = "MC|BEdit";
	public static final String LEGACY_BOOK_SIGN = "MC|BSign";
	public static final String LEGACY_SET_BEACON = "MC|Beacon";
	public static final String LEGACY_NAME_ITEM = "MC|ItemName";
	public static final String LEGACY_PICK_ITEM = "MC|PickItem";
	public static final String LEGACY_STRUCTURE_BLOCK = "MC|Struct";
	public static final String LEGACY_TRADE_SELECT = "MC|TrSel";
	public static final String LEGACY_TRADE_LIST = "MC|TrList";

	public static String toPre13(String modernName) {
		switch (modernName) {
			case MODERN_TRADE_LIST: {
				return LEGACY_TRADE_LIST;
			}
			case MODERN_BRAND: {
				return LEGACY_BRAND;
			}
			case MODERN_BOOK_OPEN: {
				return LEGACY_BOOK_OPEN;
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

	protected static final Pattern valid_pattern = Pattern.compile("[a-z0-9._-]+\\:[a-z0-9._-]*");
	protected static final Pattern invalid_pattern = Pattern.compile("[^a-z0-9._-]+");
	public static String fromPre13(String legacyName) {
		switch (legacyName) {
			case LEGACY_REGISTER: {
				return MODERN_REGISTER;
			}
			case LEGACY_UNREGISTER: {
				return MODERN_UNREGISTER;
			}
			case LEGACY_BRAND: {
				return MODERN_BRAND;
			}
			case LEGACY_BUNGEE: {
				return MODERN_BUNGEE;
			}
			default: {
				if (valid_pattern.matcher(legacyName).matches()) {
					return legacyName;
				} else {
					return NamespacedKeyUtils.combine("l", invalid_pattern.matcher(legacyName.toLowerCase()).replaceAll(""));
				}
			}
		}
	}

}
