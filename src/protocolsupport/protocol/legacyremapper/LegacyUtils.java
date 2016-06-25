package protocolsupport.protocol.legacyremapper;

import org.bukkit.ChatColor;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.utils.Utils;

public class LegacyUtils {

	public static String toText(BaseComponent component) {
		if (component == null) {
			return "";
		}
		final StringBuilder out = new StringBuilder();
		toTextSingle(out, component, component.getModifier());
		return out.toString();
	}

	private static void toTextSingle(StringBuilder out, BaseComponent component, Modifier modifier) {
		if (Utils.isTrue(modifier.hasColor())) {
			out.append(modifier.getColor());
		}
		if (Utils.isTrue(modifier.isBold())) {
			out.append(ChatColor.BOLD);
		}
		if (Utils.isTrue(modifier.isItalic())) {
			out.append(ChatColor.ITALIC);
		}
		if (Utils.isTrue(modifier.isUnderlined())) {
			out.append(ChatColor.UNDERLINE);
		}
		if (Utils.isTrue(modifier.isStrikethrough())) {
			out.append(ChatColor.STRIKETHROUGH);
		}
		if (Utils.isTrue(modifier.isRandom())) {
			out.append(ChatColor.MAGIC);
		}
		out.append(component.getValue());
		for (BaseComponent child : component.getSiblings()) {
			if (out.length() > 0) {
				out.append(ChatColor.RESET);
			}
			Modifier childmodifier = child.getModifier();
			Modifier combinedmodifier = new Modifier();
			combinedmodifier.setColor(childmodifier.hasColor() ? childmodifier.getColor() : modifier.getColor());
			combinedmodifier.setBold(childmodifier.isBold() != null ? childmodifier.isBold() : modifier.isBold());
			combinedmodifier.setBold(childmodifier.isItalic() != null ? childmodifier.isItalic() : modifier.isItalic());
			combinedmodifier.setBold(childmodifier.isUnderlined() != null ? childmodifier.isUnderlined() : modifier.isUnderlined());
			combinedmodifier.setBold(childmodifier.isStrikethrough() != null ? childmodifier.isStrikethrough() : modifier.isStrikethrough());
			combinedmodifier.setBold(childmodifier.isRandom() != null ? childmodifier.isRandom() : modifier.isRandom());
			toTextSingle(out, child, combinedmodifier);
		}
	}

	public static byte getInventoryId(String inventoryid) {
		switch (inventoryid) {
			case "minecraft:chest":
			case "minecraft:container": {
				return 0;
			}
			case "minecraft:crafting_table": {
				return 1;
			}
			case "minecraft:furnace": {
				return 2;
			}
			case "minecraft:dispenser": {
				return 3;
			}
			case "minecraft:enchanting_table": {
				return 4;
			}
			case "minecraft:brewing_stand": {
				return 5;
			}
			case "minecraft:villager": {
				return 6;
			}
			case "minecraft:beacon": {
				return 7;
			}
			case "minecraft:anvil": {
				return 8;
			}
			case "minecraft:hopper": {
				return 9;
			}
			case "minecraft:dropper": {
				return 10;
			}
			case "EntityHorse": {
				return 11;
			}
		}
		throw new IllegalArgumentException("Don't know how to convert " + inventoryid);
	}

}
