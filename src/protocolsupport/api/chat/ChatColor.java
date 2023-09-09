package protocolsupport.api.chat;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.utils.MiscUtils;
import protocolsupport.utils.reflection.ReflectionUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ChatColor {

	@SuppressWarnings("deprecation")
	private static final EnumMap<org.bukkit.ChatColor, ChatColor> BASIC_BY_BUKKIT = new EnumMap<>(org.bukkit.ChatColor.class);
	private static final Int2ObjectMap<ChatColor> BASIC_BY_RGB = new Int2ObjectOpenHashMap<>();
	private static final Map<String, ChatColor> BASIC_BY_NAME = new HashMap<>();

	public static ChatColor of(String identifier) {
		if (identifier.startsWith("#")) {
			try {
				return ofRGB(Integer.parseInt(identifier.substring(1), 16));
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return BASIC_BY_NAME.get(identifier);
		}
	}

	public static ChatColor ofRGB(int rgb) {
		ChatColor color = BASIC_BY_RGB.get(rgb);
		return color != null ? color : new ChatColor(rgb);
	}

	@SuppressWarnings("deprecation")
	public static ChatColor ofBukkit(org.bukkit.ChatColor color) {
		return BASIC_BY_BUKKIT.get(color);
	}

	public static final ChatColor BLACK = new ChatColor("black", 0x000000);
	public static final ChatColor DARK_BLUE = new ChatColor("dark_blue", 0x0000AA);
	public static final ChatColor DARK_GREEN = new ChatColor("dark_green", 0x00AA00);
	public static final ChatColor DARK_AQUA = new ChatColor("dark_aqua", 0x00AAAA);
	public static final ChatColor DARK_RED = new ChatColor("dark_red", 0xAA0000);
	public static final ChatColor DARK_PURPLE = new ChatColor("dark_purple", 0xAA00AA);
	public static final ChatColor GOLD = new ChatColor("gold", 0xFFAA00);
	public static final ChatColor GRAY = new ChatColor("gray", 0xAAAAAA);
	public static final ChatColor DARK_GRAY = new ChatColor("dark_gray", 0x555555);
	public static final ChatColor BLUE = new ChatColor("blue", 0x5555FF);
	public static final ChatColor GREEN = new ChatColor("green", 0x55FF55);
	public static final ChatColor AQUA = new ChatColor("aqua", 0x55FFFF);
	public static final ChatColor RED = new ChatColor("red", 0xFF5555);
	public static final ChatColor LIGHT_PURPLE = new ChatColor("light_purple", 0xFF55FF);
	public static final ChatColor YELLOW = new ChatColor("yellow", 0xFFFF55);
	public static final ChatColor WHITE = new ChatColor("white", 0xFFFFFF);


	@SuppressWarnings("deprecation")
	private final org.bukkit.ChatColor bukkitColor;
	private final String identifier;
	private final int rgb;
	private final int r;
	private final int g;
	private final int b;

	private ChatColor(int rgb) {
		this("#" + Integer.toHexString(rgb), rgb, false);
	}

	private ChatColor(String identifier, int rgb) {
		this(identifier, rgb, true);
	}

	@SuppressWarnings("deprecation")
	private ChatColor(String identifier, int rgb, boolean basic) {
		this.identifier = identifier;
		this.rgb = rgb;
		this.r = rgb >> 16;
		this.g = (rgb >> 8) & 0xFF;
		this.b = rgb & 0xFF;
		if (basic) {
			this.bukkitColor = org.bukkit.ChatColor.valueOf(identifier.toUpperCase(Locale.ENGLISH));
			BASIC_BY_BUKKIT.put(bukkitColor, this);
			BASIC_BY_RGB.put(rgb, this);
			BASIC_BY_NAME.put(identifier, this);
		} else {
			this.bukkitColor = null;
		}
	}

	public String getIdentifier() {
		return identifier;
	}

	public int getRGB() {
		return rgb;
	}

	public int getRed() {
		return r;
	}

	public int getGreen() {
		return g;
	}

	public int getBlue() {
		return b;
	}

	public boolean isBasic() {
		return bukkitColor != null;
	}

	public ChatColor asBasic() {
		if (isBasic()) {
			return this;
		}

		ChatColor similarColor = null;
		long similarColorDiff = Long.MAX_VALUE;
		for (ChatColor basic : BASIC_BY_BUKKIT.values()) {
			long colorDiff = MiscUtils.getColorDiff(getRed(), basic.getRed(), getGreen(), basic.getGreen(), getBlue(), basic.getBlue());
			if (colorDiff <= similarColorDiff) {
				similarColorDiff = colorDiff;
				similarColor = basic;
			}
		}
		return similarColor;
	}

	@SuppressWarnings("deprecation")
	public org.bukkit.ChatColor asBukkit() {
		if (isBasic()) {
			return bukkitColor;
		} else {
			return asBasic().bukkitColor;
		}
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

}
