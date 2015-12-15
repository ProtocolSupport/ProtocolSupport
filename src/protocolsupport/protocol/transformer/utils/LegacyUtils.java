package protocolsupport.protocol.transformer.utils;

import java.util.Iterator;

import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public class LegacyUtils {

	public static String toText(final IChatBaseComponent component) {
		if (component == null) {
			return "";
		}
		final StringBuilder out = new StringBuilder();
		Iterator<IChatBaseComponent> iterator = component.iterator();
		while (iterator.hasNext()) {
			IChatBaseComponent c = iterator.next();
			final ChatModifier modi = c.getChatModifier();
			if (modi.getColor() != null) {
				out.append(modi.getColor());
			} else {
				if (out.length() != 0) {
					out.append(EnumChatFormat.RESET);
				}
			}
			if (modi.isBold()) {
				out.append(EnumChatFormat.BOLD);
			}
			if (modi.isItalic()) {
				out.append(EnumChatFormat.ITALIC);
			}
			if (modi.isUnderlined()) {
				out.append(EnumChatFormat.UNDERLINE);
			}
			if (modi.isStrikethrough()) {
				out.append(EnumChatFormat.STRIKETHROUGH);
			}
			if (modi.isRandom()) {
				out.append(EnumChatFormat.OBFUSCATED);
			}
			out.append(c.getText());
		}
		return out.toString();
	}

	public static String getSound(String newSound) {
		switch (newSound) {
			case "game.player.hurt.fall.big":
			case "game.neutral.hurt.fall.big":
			case "game.hostile.hurt.fall.big": {
				return "damage.fallbig";
			}
			case "game.player.hurt.fall.small":
			case "game.neutral.hurt.fall.small":
			case "game.hostile.hurt.fall.small": {
				return "damage.fallsmall";
			}
			case "game.player.hurt":
			case "game.player.die":
			case "game.neutral.hurt":
			case "game.neutral.die":
			case "game.hostile.hurt":
			case "game.hostile.die": {
				return "damage.hit";
			}
			case "game.player.swim":
			case "game.neutral.swim":
			case "game.hostile.swim": {
				return "liquid.swim";
			}
			case "game.player.swim.splash":
			case "game.neutral.swim.splash":
			case "game.hostile.swim.splash": {
				return "liquid.splash";
			}
			default: {
				return newSound;
			}
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
