package protocolsupport.utils;

import io.netty.channel.Channel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.v1_8_R1.ChatModifier;
import net.minecraft.server.v1_8_R1.EnumChatFormat;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.entity.Player;

import protocolsupport.protocol.PacketDataSerializer;

public class Utils {

	@SuppressWarnings("serial")
	private static final HashMap<String, Byte> inventoryNameToId = new HashMap<String, Byte>() {
		{
			put("minecraft:chest", (byte) 0);
			put("minecraft:container", (byte) 0);
			put("minecraft:crafting_table", (byte) 1);
			put("minecraft:furnace", (byte) 2);
			put("minecraft:dispenser", (byte) 3);
			put("minecraft:enchanting_table", (byte) 4);
			put("minecraft:brewing_stand", (byte) 5);
			put("minecraft:villager", (byte) 6);
			put("minecraft:beacon", (byte) 7);
			put("minecraft:anvil", (byte) 8);
			put("minecraft:hopper", (byte) 9);
			put("minecraft:dropper", (byte) 10);
			put("EntityHorse", (byte) 11);
		}
	};

	public static byte getInventoryId(String inventoryid) {
		return inventoryNameToId.get(inventoryid);
	}

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static void writeAll(PacketDataSerializer input, PacketDataSerializer output) {
		output.writeBytes(input.readBytes(input.readableBytes()));
	}

	@SuppressWarnings("unchecked")
	public static <T> T setAccessible(AccessibleObject object) {
		object.setAccessible(true);
		return (T) object;
	}

	public static void setStaticFinalField(Field field, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		Field fieldModifiers = Field.class.getDeclaredField("modifiers");
		fieldModifiers.setAccessible(true);
		fieldModifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		Field root = Field.class.getDeclaredField("root");
		root.setAccessible(true);
		root.set(field, null);
		Field accessor = Field.class.getDeclaredField("overrideFieldAccessor");
		accessor.setAccessible(true);
		accessor.set(field, null);
		field.set(null, newValue);
	}

	public static Player getPlayer(Channel channel) {
		return ((PlayerConnection) ((NetworkManager) channel.pipeline().get("packet_handler")).getPacketListener()).player.getBukkitEntity();
	}

	public static List<int[]> splitArray(int[] array, int limit) {
		List<int[]> list = new ArrayList<int[]>();
		if (array.length <= limit) {
			list.add(array);
			return list;
		}
		int copied = 0;
		int count = array.length / limit;
		if ((array.length % limit) != 0) {
			count++;
		}
		for (int i = 0; i < count; i++) {
			list.add(Arrays.copyOfRange(array, copied, Math.min(array.length, copied + limit)));
			copied+= limit;
		}
		return list;
	}

	public static String fromComponent(final IChatBaseComponent component) {
		if (component == null) {
			return "";
		}
		final StringBuilder out = new StringBuilder();
		@SuppressWarnings("unchecked")
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

}
