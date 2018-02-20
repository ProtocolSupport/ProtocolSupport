package protocolsupport.protocol.typeremapper.legacy;

import org.bukkit.ChatColor;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.utils.Utils;

public class LegacyChat {

	public static String toText(BaseComponent component, String locale) {
		if (component == null) {
			return "";
		}
		final StringBuilder out = new StringBuilder();
		toTextSingle(out, locale, component, component.getModifier());
		while (true) {
			int colorcharsearchindex = out.length() - 2;
			if ((colorcharsearchindex > 0) && (out.charAt(colorcharsearchindex) == ChatColor.COLOR_CHAR)) {
				out.delete(colorcharsearchindex, out.length());
			} else {
				break;
			}
		}
		return out.toString();
	}

	private static void toTextSingle(StringBuilder out, String locale, BaseComponent component, Modifier modifier) {
		if (Utils.isTrue(modifier.hasColor())) {
			out.append(modifier.getColor());
		}
		boolean hadFormat = false;
		if (Utils.isTrue(modifier.isBold())) {
			out.append(ChatColor.BOLD);
			hadFormat = true;
		}
		if (!hadFormat && Utils.isTrue(modifier.isItalic())) {
			out.append(ChatColor.ITALIC);
			hadFormat = true;
		}
		if (!hadFormat && Utils.isTrue(modifier.isUnderlined())) {
			out.append(ChatColor.UNDERLINE);
			hadFormat = true;
		}
		if (!hadFormat && Utils.isTrue(modifier.isStrikethrough())) {
			out.append(ChatColor.STRIKETHROUGH);
			hadFormat = true;
		}
		if (!hadFormat && Utils.isTrue(modifier.isRandom())) {
			out.append(ChatColor.MAGIC);
		}
		out.append(component.getValue(locale));
		out.append(ChatColor.RESET);
		for (BaseComponent child : component.getSiblings()) {
			Modifier childmodifier = child.getModifier();
			Modifier combinedmodifier = new Modifier();
			combinedmodifier.setColor(childmodifier.hasColor() ? childmodifier.getColor() : modifier.getColor());
			combinedmodifier.setBold(childmodifier.isBold() != null ? childmodifier.isBold() : modifier.isBold());
			combinedmodifier.setItalic(childmodifier.isItalic() != null ? childmodifier.isItalic() : modifier.isItalic());
			combinedmodifier.setUnderlined(childmodifier.isUnderlined() != null ? childmodifier.isUnderlined() : modifier.isUnderlined());
			combinedmodifier.setStrikethrough(childmodifier.isStrikethrough() != null ? childmodifier.isStrikethrough() : modifier.isStrikethrough());
			combinedmodifier.setRandom(childmodifier.isRandom() != null ? childmodifier.isRandom() : modifier.isRandom());
			toTextSingle(out, locale, child, combinedmodifier);
		}
	}

}
