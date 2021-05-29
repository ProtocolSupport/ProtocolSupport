package protocolsupport.protocol.typeremapper.legacy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import protocolsupport.api.chat.ChatColor;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.modifiers.Modifier;

public class LegacyChat {

	private LegacyChat() {
	}

	public static final char STYLE_CHAR = '§';

	public static final char STYLE_RANDOM_CHAR = 'k';
	public static final char STYLE_BOLD_CHAR = 'l';
	public static final char STYLE_STRIKETHROUGH_CHAR = 'm';
	public static final char STYLE_UNDERLINED_CHAR = 'n';
	public static final char STYLE_ITALIC_CHAR = 'o';
	public static final char STYLE_RESET_CHAR = 'r';

	public static final String STYLE_RANDOM = String.valueOf(new char[] {STYLE_CHAR, STYLE_RANDOM_CHAR});
	public static final String STYLE_BOLD = String.valueOf(new char[] {STYLE_CHAR, STYLE_BOLD_CHAR});
	public static final String STYLE_STRIKETHROUGH = String.valueOf(new char[] {STYLE_CHAR, STYLE_STRIKETHROUGH_CHAR});
	public static final String STYLE_UNDERLINED = String.valueOf(new char[] {STYLE_CHAR, STYLE_UNDERLINED_CHAR});
	public static final String STYLE_ITALIC = String.valueOf(new char[] {STYLE_CHAR, STYLE_ITALIC_CHAR});
	public static final String STYLE_RESET = String.valueOf(new char[] {STYLE_CHAR, STYLE_RESET_CHAR});


	public static boolean isEmpty(@Nonnull String string) {
		if (string.isEmpty()) {
			return true;
		}
		int length = string.length();
		int index = 0;
		do {
			if (string.charAt(index) != STYLE_CHAR) {
				return false;
			} else {
				index += 2;
			}
		} while (index < length);
		return true;
	}

	public static String toText(@Nullable BaseComponent component, @Nonnull String locale) {
		if (component == null) {
			return "";
		}
		LegacyChatConverterContext context = new LegacyChatConverterContext(locale);
		toTextSingle(context, component, component.getModifier());
		return context.getText();
	}

	protected static class LegacyChatConverterContext {

		protected final StringBuilder out = new StringBuilder();
		protected final String locale;
		protected Modifier lastAppendedModifier = new Modifier();

		public LegacyChatConverterContext(String locale) {
			this.locale = locale;
		}

		public String getLocale() {
			return locale;
		}

		public String getText() {
			return out.toString();
		}

		public void writeValue(String value) {
			out.append(value);
		}

		/*
		 * Appends colors codes calculating needed ones
		 * Allows us to not append redundant color codes
		 *
		 * Color codes cancel previous style
		 * So after switching color codes we need to append all format codes
		 * If we don't need to switch color codes, we first try to only append additional format codes (if none of the previous format codes should be cancelled)
		 * If we need to switch to completely new format codes we write the last appended color/reset code to cancel previous formatting codes first
		 *
		 * If changing color {
		 *   If has new color {
		 *   	Append new color code to stringbuilder
		 *   } else {
		 *   	Append reset to stringbuilder
		 *   }
		 *   Append all format codes to stringbuilder
		 * } else {
		 *   try {
		 *      Remember stringbuilder length
		 *      Append additional format codes to stringbuilder
		 *   } catch (Format reset needed) {
		 *      Trim stringbuilder to old length
		 *      If has prev color {
		 *        Append prev color to stringbuilder
		 *      } else {
		 *        Append reset to stringbuilder
		 *      }
		 *      Append all format codes to stringbuilder
		 *   }
		 * }
		 */
		public void writeModifier(Modifier newModifier) {
			org.bukkit.ChatColor lastAppendedColor = lastAppendedModifier.hasColor() ? lastAppendedModifier.getRGBColor().asBukkit() : null;
			org.bukkit.ChatColor newColor = newModifier.hasColor() ? newModifier.getRGBColor().asBukkit() : null;
			if (newColor != lastAppendedColor) {
				if (newColor != null) {
					out.append(newColor);
				} else {
					out.append(STYLE_RESET);
				}
				writeAllFormatCodes(newModifier);
			} else {
				int prevLength = out.length();
				try {
					writeAdditionalFormatCode(lastAppendedModifier.isRandom(), newModifier.isRandom(), STYLE_RANDOM);
					writeAdditionalFormatCode(lastAppendedModifier.isBold(), newModifier.isBold(), STYLE_BOLD);
					writeAdditionalFormatCode(lastAppendedModifier.isStrikethrough(), newModifier.isStrikethrough(), STYLE_STRIKETHROUGH);
					writeAdditionalFormatCode(lastAppendedModifier.isUnderlined(), newModifier.isUnderlined(), STYLE_UNDERLINED);
					writeAdditionalFormatCode(lastAppendedModifier.isItalic(), newModifier.isItalic(), STYLE_ITALIC);
				} catch (NeedsFormatResetSignal e) {
					out.setLength(prevLength);
					if (lastAppendedColor != null) {
						out.append(lastAppendedColor);
					} else {
						out.append(STYLE_RESET);
					}
					writeAllFormatCodes(newModifier);
				}
			}
			lastAppendedModifier = newModifier;
		}

		/*
		 * Writes additional format value if needed or throws exception if reset is needed
		 *
		 * Following combinations of modifier values are possible:
		 * 1) null, null
		 * 2) null, true
		 * 3) null, false
		 * 4) true, null
		 * 5) true, true
		 * 6) true, false
		 * 7) false, null
		 * 8) false, true
		 * 9) false, false
		 *
		 * We need to reset format codes if we encounter combinations 4, 6
		 * We need to write additional format code is we encounter combinations 2, 8
		 */
		protected boolean writeAdditionalFormatCode(Boolean oldModifierValue, Boolean newModifierValue, String newFormatCode) throws NeedsFormatResetSignal {
			if (Boolean.TRUE.equals(oldModifierValue) && !Boolean.TRUE.equals(newModifierValue)) {
				throw NeedsFormatResetSignal.INSTANCE;
			}
			if (!Boolean.TRUE.equals(oldModifierValue) && Boolean.TRUE.equals(newModifierValue)) {
				out.append(newFormatCode);
			}
			return true;
		}

		protected void writeAllFormatCodes(Modifier modifier) {
			if (Boolean.TRUE.equals(modifier.isRandom())) {
				out.append(STYLE_RANDOM);
			}
			if (Boolean.TRUE.equals(modifier.isBold())) {
				out.append(STYLE_BOLD);
			}
			if (Boolean.TRUE.equals(modifier.isStrikethrough())) {
				out.append(STYLE_STRIKETHROUGH);
			}
			if (Boolean.TRUE.equals(modifier.isUnderlined())) {
				out.append(STYLE_UNDERLINED);
			}
			if (Boolean.TRUE.equals(modifier.isItalic())) {
				out.append(STYLE_ITALIC);
			}
		}

		protected static class NeedsFormatResetSignal extends Exception {
			private static final long serialVersionUID = 1L;
			public static final NeedsFormatResetSignal INSTANCE = new NeedsFormatResetSignal();
			@Override
			public synchronized Throwable fillInStackTrace() {
				return this;
			}
		}

	}

	/*
	 * Recursively converts a component to legacy text
	 */
	protected static void toTextSingle(LegacyChatConverterContext context, BaseComponent component, Modifier currentModifier) {
		context.writeModifier(currentModifier);
		context.writeValue(component.getValue(context.getLocale()));
		for (BaseComponent child : component.getSiblings()) {
			toTextSingle(context, child, combineModifiers(currentModifier, child.getModifier()));
		}
	}

	/*
	 * Combines parent and child modifiers
	 * If child modifier doesn't have a specific style set, it is copied from parent one
	 */
	protected static Modifier combineModifiers(Modifier parentModifier, Modifier childModifier) {
		Modifier combinedModifier = new Modifier();
		combinedModifier.setRGBColor(childModifier.hasColor() ? childModifier.getRGBColor() : parentModifier.getRGBColor());
		combinedModifier.setBold(childModifier.isBold() != null ? childModifier.isBold() : parentModifier.isBold());
		combinedModifier.setItalic(childModifier.isItalic() != null ? childModifier.isItalic() : parentModifier.isItalic());
		combinedModifier.setUnderlined(childModifier.isUnderlined() != null ? childModifier.isUnderlined() : parentModifier.isUnderlined());
		combinedModifier.setStrikethrough(childModifier.isStrikethrough() != null ? childModifier.isStrikethrough() : parentModifier.isStrikethrough());
		combinedModifier.setRandom(childModifier.isRandom() != null ? childModifier.isRandom() : parentModifier.isRandom());
		return combinedModifier;
	}


	public static BaseComponent fromMessage(String message) {
		int length = message.length();

		List<BaseComponent> components = new ArrayList<>();

		BaseComponent lastComoponent = null;
		StringBuilder text = new StringBuilder();
		Modifier currentModifier = new Modifier();

		charIter: for (int cIndex = 0; cIndex < length; ++cIndex) {
			char c = message.charAt(cIndex);
			if (c == STYLE_CHAR) {
				if (++cIndex >= length) {
					break;
				}
				c = message.charAt(cIndex);

				if (text.length() > 0) {
					BaseComponent component = new TextComponent(text.toString()).withModifier(currentModifier);
					if ((lastComoponent == null) || currentModifier.hasColor()) {
						components.add(component);
					} else {
						lastComoponent.addSibling(component);
					}
					lastComoponent = component;
					text.setLength(0);
					currentModifier = new Modifier();
				}

				switch (Character.toLowerCase(c)) {
					case 'x': {
						int rgbIndex = cIndex + 2;
						if ((cIndex += 12) >= length) {
							break charIter;
						}
						currentModifier.setRGBColor(ChatColor.ofRGB(
							(Character.digit(message.charAt(rgbIndex), 16) << 20) |
							(Character.digit(message.charAt(rgbIndex + 2), 16) << 16) |
							(Character.digit(message.charAt(rgbIndex + 4), 16) << 12) |
							(Character.digit(message.charAt(rgbIndex + 6), 16) << 8) |
							(Character.digit(message.charAt(rgbIndex + 8), 16) << 4) |
							(Character.digit(message.charAt(rgbIndex + 10), 16))
						));
						break;
					}
					case '0': {
						currentModifier.setRGBColor(ChatColor.BLACK);
						break;
					}
					case '1': {
						currentModifier.setRGBColor(ChatColor.DARK_BLUE);
						break;
					}
					case '2': {
						currentModifier.setRGBColor(ChatColor.DARK_GREEN);
						break;
					}
					case '3': {
						currentModifier.setRGBColor(ChatColor.DARK_AQUA);
						break;
					}
					case '4': {
						currentModifier.setRGBColor(ChatColor.DARK_RED);
						break;
					}
					case '5': {
						currentModifier.setRGBColor(ChatColor.DARK_PURPLE);
						break;
					}
					case '6': {
						currentModifier.setRGBColor(ChatColor.GOLD);
						break;
					}
					case '7': {
						currentModifier.setRGBColor(ChatColor.GRAY);
						break;
					}
					case '8': {
						currentModifier.setRGBColor(ChatColor.DARK_GRAY);
						break;
					}
					case '9': {
						currentModifier.setRGBColor(ChatColor.BLUE);
						break;
					}
					case 'a': {
						currentModifier.setRGBColor(ChatColor.GREEN);
						break;
					}
					case 'b': {
						currentModifier.setRGBColor(ChatColor.AQUA);
						break;
					}
					case 'c': {
						currentModifier.setRGBColor(ChatColor.RED);
						break;
					}
					case 'd': {
						currentModifier.setRGBColor(ChatColor.LIGHT_PURPLE);
						break;
					}
					case 'e': {
						currentModifier.setRGBColor(ChatColor.YELLOW);
						break;
					}
					case 'f': {
						currentModifier.setRGBColor(ChatColor.WHITE);
						break;
					}
					case STYLE_RANDOM_CHAR: {
						currentModifier.setRandom(Boolean.TRUE);
						break;
					}
					case STYLE_BOLD_CHAR: {
						currentModifier.setBold(Boolean.TRUE);
						break;
					}
					case STYLE_STRIKETHROUGH_CHAR: {
						currentModifier.setStrikethrough(Boolean.TRUE);
						break;
					}
					case STYLE_UNDERLINED_CHAR: {
						currentModifier.setUnderlined(Boolean.TRUE);
						break;
					}
					case STYLE_ITALIC_CHAR: {
						currentModifier.setItalic(Boolean.TRUE);
						break;
					}
					default: {
						lastComoponent = null;
						break;
					}
				}
			} else {
				text.append(c);
			}
		}

		if (text.length() > 0) {
			BaseComponent component = new TextComponent(text.toString()).withModifier(currentModifier);
			if ((lastComoponent == null) || currentModifier.hasColor()) {
				components.add(component);
			} else {
				lastComoponent.addSibling(component);
			}
		}

		switch (components.size()) {
			case 0: {
				return new TextComponent("");
			}
			case 1: {
				return components.get(0);
			}
			default: {
				return new TextComponent("").withSiblings(components);
			}
		}
	}

	public static String clampLegacyText(String text, int limit) {
		if (text.length() <= limit) {
			return text;
		}
		int lastNotColorCharIndex = limit - 1;
		while (text.charAt(lastNotColorCharIndex) == STYLE_CHAR) {
			lastNotColorCharIndex--;
		}
		return text.substring(0, lastNotColorCharIndex + 1);
	}

}
