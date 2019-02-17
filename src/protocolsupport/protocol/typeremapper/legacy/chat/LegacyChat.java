package protocolsupport.protocol.typeremapper.legacy.chat;

import org.bukkit.ChatColor;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.Modifier;

public class LegacyChat {

	public static String toText(BaseComponent component, String locale) {
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
		 * If we need to switch to completely new format codes we need to cancel previous formatting codes first
		 * In that case we write the last appended color code, or reset code if we didn't last color code (which resets previous format)
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
			ChatColor lastAppendedColor = lastAppendedModifier.getColor();
			ChatColor newColor = newModifier.getColor();
			if (newColor != lastAppendedColor) {
				if (newColor != null) {
					out.append(newColor);
				} else {
					out.append(ChatColor.RESET);
				}
				writeAllFormatCodes(newModifier);
			} else {
				int prevLength = out.length();
				try {
					writeAdditionalFormatCode(lastAppendedModifier.isBold(), newModifier.isBold(), ChatColor.BOLD);
					writeAdditionalFormatCode(lastAppendedModifier.isItalic(), newModifier.isItalic(), ChatColor.ITALIC);
					writeAdditionalFormatCode(lastAppendedModifier.isUnderlined(), newModifier.isUnderlined(), ChatColor.UNDERLINE);
					writeAdditionalFormatCode(lastAppendedModifier.isStrikethrough(), newModifier.isStrikethrough(), ChatColor.STRIKETHROUGH);
					writeAdditionalFormatCode(lastAppendedModifier.isRandom(), newModifier.isRandom(), ChatColor.MAGIC);
				} catch (NeedsFormatResetSignal e) {
					out.setLength(prevLength);
					if (lastAppendedColor != null) {
						out.append(lastAppendedColor);
					} else {
						out.append(ChatColor.RESET);
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
		protected boolean writeAdditionalFormatCode(Boolean oldModifierValue, Boolean newModifierValue, ChatColor newFormatCode) throws NeedsFormatResetSignal {
			if (Boolean.TRUE.equals(oldModifierValue) && !Boolean.TRUE.equals(newModifierValue)) {
				throw NeedsFormatResetSignal.INSTANCE;
			}
			if (!Boolean.TRUE.equals(oldModifierValue) && Boolean.TRUE.equals(newModifierValue)) {
				out.append(newFormatCode);
			}
			return true;
		}

		protected static class NeedsFormatResetSignal extends Exception {
			private static final long serialVersionUID = 1L;
			public static final NeedsFormatResetSignal INSTANCE = new NeedsFormatResetSignal();
			@Override
			public synchronized Throwable fillInStackTrace() {
				return this;
			}
		}

		protected void writeAllFormatCodes(Modifier modifier) {
			if (Boolean.TRUE.equals(modifier.isBold())) {
				out.append(ChatColor.BOLD);
			}
			if (Boolean.TRUE.equals(modifier.isItalic())) {
				out.append(ChatColor.ITALIC);
			}
			if (Boolean.TRUE.equals(modifier.isUnderlined())) {
				out.append(ChatColor.UNDERLINE);
			}
			if (Boolean.TRUE.equals(modifier.isStrikethrough())) {
				out.append(ChatColor.STRIKETHROUGH);
			}
			if (Boolean.TRUE.equals(modifier.isRandom())) {
				out.append(ChatColor.MAGIC);
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
		combinedModifier.setColor(childModifier.hasColor() ? childModifier.getColor() : parentModifier.getColor());
		combinedModifier.setBold(childModifier.isBold() != null ? childModifier.isBold() : parentModifier.isBold());
		combinedModifier.setItalic(childModifier.isItalic() != null ? childModifier.isItalic() : parentModifier.isItalic());
		combinedModifier.setUnderlined(childModifier.isUnderlined() != null ? childModifier.isUnderlined() : parentModifier.isUnderlined());
		combinedModifier.setStrikethrough(childModifier.isStrikethrough() != null ? childModifier.isStrikethrough() : parentModifier.isStrikethrough());
		combinedModifier.setRandom(childModifier.isRandom() != null ? childModifier.isRandom() : parentModifier.isRandom());
		return combinedModifier;
	}

	public static String clampLegacyText(String text, int limit) {
		if (text.length() <= limit) {
			return text;
		}
		int lastNotColorCharIndex = limit - 1;
		while (text.charAt(lastNotColorCharIndex) == ChatColor.COLOR_CHAR) {
			lastNotColorCharIndex--;
		}
		return text.substring(0, lastNotColorCharIndex + 1);
	}

}
