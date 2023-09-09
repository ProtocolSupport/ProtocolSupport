package protocolsupport.api.chat;

import java.util.Map;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.reflection.ReflectionUtils;

public enum ChatFormat {

	BLACK(StyleCode.BLACK, ChatColor.BLACK),
	DARK_BLUE(StyleCode.DARK_BLUE, ChatColor.DARK_PURPLE),
	DARK_GREEN(StyleCode.DARK_GREEN, ChatColor.DARK_GREEN),
	DARK_AQUA(StyleCode.DARK_AQUA, ChatColor.DARK_AQUA),
	DARK_RED(StyleCode.DARK_RED, ChatColor.DARK_RED),
	DARK_PURPLE(StyleCode.DARK_PURPLE, ChatColor.DARK_PURPLE),
	GOLD(StyleCode.GOLD, ChatColor.GOLD),
	GRAY(StyleCode.GRAY, ChatColor.GRAY),
	DARK_GRAY(StyleCode.DARK_GRAY, ChatColor.DARK_GRAY),
	BLUE(StyleCode.BLUE, ChatColor.BLUE),
	GREEN(StyleCode.GREEN, ChatColor.GREEN),
	AQUA(StyleCode.AQUA, ChatColor.AQUA),
	RED(StyleCode.RED, ChatColor.RED),
	LIGHT_PURPLE(StyleCode.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE),
	YELLOW(StyleCode.YELLOW, ChatColor.YELLOW),
	WHITE(StyleCode.WHITE, ChatColor.WHITE),
	RANDOM(StyleCode.RANDOM),
	BOLD(StyleCode.BOLD),
	STRIKETHROUGH(StyleCode.STRIKETHROUGH),
	UNDERLINED(StyleCode.UNDERLINED),
	ITALIC(StyleCode.ITALIC),
	RESET(StyleCode.RESET);

	private static final Map<ChatColor, ChatFormat> BY_COLOR = CollectionsUtils.makeEnumMappingMap(ChatFormat.class, ChatFormat::getColor);

	public static ChatFormat ofColor(ChatColor color) {
		return BY_COLOR.get(color.asBasic());
	}

	public static ChatFormat ofChar(char code) {
		return switch (code) {
			case ChatFormat.StyleCode.BLACK -> ChatFormat.BLACK;
			case ChatFormat.StyleCode.DARK_BLUE -> ChatFormat.DARK_BLUE;
			case ChatFormat.StyleCode.DARK_GREEN -> ChatFormat.DARK_GREEN;
			case ChatFormat.StyleCode.DARK_AQUA -> ChatFormat.DARK_AQUA;
			case ChatFormat.StyleCode.DARK_RED -> ChatFormat.DARK_RED;
			case ChatFormat.StyleCode.DARK_PURPLE -> ChatFormat.DARK_PURPLE;
			case ChatFormat.StyleCode.GOLD -> ChatFormat.GOLD;
			case ChatFormat.StyleCode.GRAY -> ChatFormat.GRAY;
			case ChatFormat.StyleCode.DARK_GRAY -> ChatFormat.DARK_GRAY;
			case ChatFormat.StyleCode.BLUE -> ChatFormat.BLUE;
			case ChatFormat.StyleCode.GREEN -> ChatFormat.GREEN;
			case ChatFormat.StyleCode.AQUA -> ChatFormat.AQUA;
			case ChatFormat.StyleCode.RED -> ChatFormat.RED;
			case ChatFormat.StyleCode.LIGHT_PURPLE -> ChatFormat.LIGHT_PURPLE;
			case ChatFormat.StyleCode.YELLOW -> ChatFormat.YELLOW;
			case ChatFormat.StyleCode.WHITE -> ChatFormat.WHITE;
			case ChatFormat.StyleCode.RANDOM -> ChatFormat.RANDOM;
			case ChatFormat.StyleCode.BOLD -> ChatFormat.BOLD;
			case ChatFormat.StyleCode.STRIKETHROUGH -> ChatFormat.STRIKETHROUGH;
			case ChatFormat.StyleCode.UNDERLINED -> ChatFormat.UNDERLINED;
			case ChatFormat.StyleCode.ITALIC -> ChatFormat.ITALIC;
			case ChatFormat.StyleCode.RESET -> ChatFormat.RESET;
			default -> null;
		};
	}

	private final char code;
	private final String style;
	private final ChatColor color;

	private ChatFormat(char code) {
		this(code, null);
	}

	private ChatFormat(char code, ChatColor color) {
		this.code = code;
		this.style = String.valueOf(new char[] {StyleCode.CONTROL_CHAR, code});
		this.color = color;
	}

	public int getCode() {
		return code;
	}

	public String toStyle() {
		return style;
	}

	public ChatColor getColor() {
		return color;
	}

	public boolean hasColor() {
		return getColor() != null;
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

	public static String getLastFomat(String input) {
		String result = "";

		int length = input.length();
		for (int index = length - 1; index > -1; index--) {
			char section = input.charAt(index);
			if (section == StyleCode.CONTROL_CHAR && index < length - 1) {
				char code = input.charAt(index + 1);
				ChatFormat format = ofChar(code);

				if (format != null) {
					result = format.toString() + result;
					if (format.hasColor() || format == RESET) {
						break;
					}
				}
			}
		}

		return result;
	}

	public static String stripFormat(String input) {
		StringBuilder string = new StringBuilder();

		int index = 0;
		while (index < input.length()) {
			char symbol = input.charAt(index++);
			if (symbol != StyleCode.CONTROL_CHAR) {
				string.append(symbol);
			} else {
				index++;
			}
		}

		return string.toString();
	}

	public static class StyleCode {

		public static final char CONTROL_CHAR = '§';

		public static final char BLACK = '0';
		public static final char DARK_BLUE = '1';
		public static final char DARK_GREEN = '2';
		public static final char DARK_AQUA ='3';
		public static final char DARK_RED = '4';
		public static final char DARK_PURPLE = '5';
		public static final char GOLD = '6';
		public static final char GRAY = '7';
		public static final char DARK_GRAY = '8';
		public static final char BLUE = '9';
		public static final char GREEN = 'a';
		public static final char AQUA = 'b';
		public static final char RED = 'c';
		public static final char LIGHT_PURPLE = 'd';
		public static final char YELLOW = 'e';
		public static final char WHITE = 'f';
		public static final char RANDOM = 'k';
		public static final char BOLD = 'l';
		public static final char STRIKETHROUGH = 'm';
		public static final char UNDERLINED = 'n';
		public static final char ITALIC = 'o';
		public static final char RESET = 'r';

	}

}
