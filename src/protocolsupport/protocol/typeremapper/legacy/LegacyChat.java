package protocolsupport.protocol.typeremapper.legacy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import protocolsupport.api.chat.ChatColor;
import protocolsupport.api.chat.ChatFormat;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.modifiers.Modifier;

public class LegacyChat {

	private LegacyChat() {
	}

	public static boolean isEmpty(@Nonnull String string) {
		if (string.isEmpty()) {
			return true;
		}
		int length = string.length();
		int index = 0;
		do {
			if (string.charAt(index) != ChatFormat.StyleCode.CONTROL_CHAR) {
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
			ChatFormat lastAppendedColor = lastAppendedModifier.hasColor() ? ChatFormat.ofColor(lastAppendedModifier.getRGBColor()) : null;
			ChatFormat newColor = newModifier.hasColor() ? ChatFormat.ofColor(newModifier.getRGBColor()) : null;
			if (newColor != lastAppendedColor) {
				if (newColor != null) {
					out.append(newColor.toStyle());
				} else {
					out.append(ChatFormat.RESET.toStyle());
				}
				writeAllFormatCodes(newModifier);
			} else {
				int prevLength = out.length();
				try {
					writeAdditionalFormatCode(lastAppendedModifier.isRandom(), newModifier.isRandom(), ChatFormat.RANDOM);
					writeAdditionalFormatCode(lastAppendedModifier.isBold(), newModifier.isBold(), ChatFormat.BOLD);
					writeAdditionalFormatCode(lastAppendedModifier.isStrikethrough(), newModifier.isStrikethrough(), ChatFormat.STRIKETHROUGH);
					writeAdditionalFormatCode(lastAppendedModifier.isUnderlined(), newModifier.isUnderlined(), ChatFormat.UNDERLINED);
					writeAdditionalFormatCode(lastAppendedModifier.isItalic(), newModifier.isItalic(), ChatFormat.ITALIC);
				} catch (NeedsFormatResetSignal e) {
					out.setLength(prevLength);
					if (lastAppendedColor != null) {
						out.append(lastAppendedColor);
					} else {
						out.append(ChatFormat.RESET.toStyle());
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
		protected void writeAdditionalFormatCode(Boolean oldModifierValue, Boolean newModifierValue, ChatFormat style) throws NeedsFormatResetSignal {
			if (Boolean.TRUE.equals(oldModifierValue) && !Boolean.TRUE.equals(newModifierValue)) {
				throw NeedsFormatResetSignal.INSTANCE;
			}
			if (!Boolean.TRUE.equals(oldModifierValue) && Boolean.TRUE.equals(newModifierValue)) {
				out.append(style.toStyle());
			}
		}

		protected void writeAllFormatCodes(Modifier modifier) {
			if (Boolean.TRUE.equals(modifier.isRandom())) {
				out.append(ChatFormat.RANDOM.toStyle());
			}
			if (Boolean.TRUE.equals(modifier.isBold())) {
				out.append(ChatFormat.BOLD.toStyle());
			}
			if (Boolean.TRUE.equals(modifier.isStrikethrough())) {
				out.append(ChatFormat.STRIKETHROUGH.toStyle());
			}
			if (Boolean.TRUE.equals(modifier.isUnderlined())) {
				out.append(ChatFormat.UNDERLINED.toStyle());
			}
			if (Boolean.TRUE.equals(modifier.isItalic())) {
				out.append(ChatFormat.ITALIC.toStyle());
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
			if (c == ChatFormat.StyleCode.CONTROL_CHAR) {
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
					case ChatFormat.StyleCode.BLACK: {
						currentModifier.setRGBColor(ChatColor.BLACK);
						break;
					}
					case ChatFormat.StyleCode.DARK_BLUE: {
						currentModifier.setRGBColor(ChatColor.DARK_BLUE);
						break;
					}
					case ChatFormat.StyleCode.DARK_GREEN: {
						currentModifier.setRGBColor(ChatColor.DARK_GREEN);
						break;
					}
					case ChatFormat.StyleCode.DARK_AQUA: {
						currentModifier.setRGBColor(ChatColor.DARK_AQUA);
						break;
					}
					case ChatFormat.StyleCode.DARK_RED: {
						currentModifier.setRGBColor(ChatColor.DARK_RED);
						break;
					}
					case ChatFormat.StyleCode.DARK_PURPLE: {
						currentModifier.setRGBColor(ChatColor.DARK_PURPLE);
						break;
					}
					case ChatFormat.StyleCode.GOLD: {
						currentModifier.setRGBColor(ChatColor.GOLD);
						break;
					}
					case ChatFormat.StyleCode.GRAY: {
						currentModifier.setRGBColor(ChatColor.GRAY);
						break;
					}
					case ChatFormat.StyleCode.DARK_GRAY: {
						currentModifier.setRGBColor(ChatColor.DARK_GRAY);
						break;
					}
					case ChatFormat.StyleCode.BLUE: {
						currentModifier.setRGBColor(ChatColor.BLUE);
						break;
					}
					case ChatFormat.StyleCode.GREEN: {
						currentModifier.setRGBColor(ChatColor.GREEN);
						break;
					}
					case ChatFormat.StyleCode.AQUA: {
						currentModifier.setRGBColor(ChatColor.AQUA);
						break;
					}
					case ChatFormat.StyleCode.RED: {
						currentModifier.setRGBColor(ChatColor.RED);
						break;
					}
					case ChatFormat.StyleCode.LIGHT_PURPLE: {
						currentModifier.setRGBColor(ChatColor.LIGHT_PURPLE);
						break;
					}
					case ChatFormat.StyleCode.YELLOW: {
						currentModifier.setRGBColor(ChatColor.YELLOW);
						break;
					}
					case ChatFormat.StyleCode.WHITE: {
						currentModifier.setRGBColor(ChatColor.WHITE);
						break;
					}
					case ChatFormat.StyleCode.RANDOM: {
						currentModifier.setRandom(Boolean.TRUE);
						break;
					}
					case ChatFormat.StyleCode.BOLD: {
						currentModifier.setBold(Boolean.TRUE);
						break;
					}
					case ChatFormat.StyleCode.STRIKETHROUGH: {
						currentModifier.setStrikethrough(Boolean.TRUE);
						break;
					}
					case ChatFormat.StyleCode.UNDERLINED: {
						currentModifier.setUnderlined(Boolean.TRUE);
						break;
					}
					case ChatFormat.StyleCode.ITALIC: {
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

		return switch (components.size()) {
			case 0 -> new TextComponent("");
			case 1 -> components.get(0);
			default -> new TextComponent("").withSiblings(components);
		};
	}

	public static String clampLegacyText(String text, int limit) {
		if (text.length() <= limit) {
			return text;
		}
		int lastNotColorCharIndex = limit - 1;
		while (text.charAt(lastNotColorCharIndex) == ChatFormat.StyleCode.CONTROL_CHAR) {
			lastNotColorCharIndex--;
		}
		return text.substring(0, lastNotColorCharIndex + 1);
	}

}
