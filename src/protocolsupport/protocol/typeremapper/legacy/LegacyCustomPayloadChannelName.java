package protocolsupport.protocol.typeremapper.legacy;

import java.util.regex.Pattern;

import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyCustomPayloadChannelName {

	private LegacyCustomPayloadChannelName() {
	}

	//TODO: move modern channels to separate type
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

	public static String toLegacy(String modernName) {
		return switch (modernName) {
			case MODERN_REGISTER -> LEGACY_REGISTER;
			case MODERN_UNREGISTER -> LEGACY_UNREGISTER;
			case MODERN_BRAND -> LEGACY_BRAND;
			case MODERN_BUNGEE -> LEGACY_BUNGEE;
			case MODERN_TRADE_LIST -> LEGACY_TRADE_LIST;
			case MODERN_BOOK_OPEN -> LEGACY_BOOK_OPEN;
			default -> modernName;
		};
	}

	protected static final Pattern valid_pattern = Pattern.compile("[a-z0-9._-]+\\:[a-z0-9._-]*");

	public static String fromLegacy(String legacyName) {
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
			case MODERN_BUNGEE: {
				return null;
			}
			default: {
				if (valid_pattern.matcher(legacyName).matches()) {
					return legacyName;
				} else {
					return null;
				}
			}
		}
	}

}
