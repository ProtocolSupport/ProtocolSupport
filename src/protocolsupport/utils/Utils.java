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
import java.util.UUID;

import net.minecraft.server.v1_8_R1.ChatModifier;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumChatFormat;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.entity.Player;

import protocolsupport.protocol.ChannelHandlers;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ProtocolVersion;

public class Utils {

	public static byte getInventoryId(String inventoryid) {
		switch (inventoryid.toLowerCase()) {
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
		throw new IllegalArgumentException("Don't know how to convert "+inventoryid);
	}

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static void writeAll(PacketDataSerializer input, PacketDataSerializer output) {
		output.writeBytes(input.readBytes(input.readableBytes()));
	}

	public static <T extends AccessibleObject> T setAccessible(T object) {
		object.setAccessible(true);
		return object;
	}

	public static void setStaticFinalField(Field field, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		setAccessible(Field.class.getDeclaredField("modifiers")).setInt(field, field.getModifiers() & ~Modifier.FINAL);
		setAccessible(Field.class.getDeclaredField("root")).set(field, null);
		setAccessible(Field.class.getDeclaredField("overrideFieldAccessor")).set(field, null);
		setAccessible(field).set(null, newValue);
	}

	public static Player getPlayer(Channel channel) {
		return ((PlayerConnection) ((NetworkManager) channel.pipeline().get(ChannelHandlers.NETWORK_MANAGER)).getPacketListener()).player.getBukkitEntity();
	}

	@SuppressWarnings("serial")
	private static final HashMap<UUID, ProtocolVersion> uuidToVersion = new HashMap<UUID, ProtocolVersion>() {{
		put(ProtocolVersion.MINECRAFT_1_7_10_UUID_ID, ProtocolVersion.MINECRAFT_1_7_10);
		put(ProtocolVersion.MINECRAFT_1_7_5_UUID_ID, ProtocolVersion.MINECRAFT_1_7_5);
		put(ProtocolVersion.MINECRAFT_1_6_4_UUID_ID, ProtocolVersion.MINECRAFT_1_6_4);
		put(ProtocolVersion.MINECRAFT_1_6_2_UUID_ID, ProtocolVersion.MINECRAFT_1_6_2);
		put(ProtocolVersion.MINECRAFT_1_5_2_UUID_ID, ProtocolVersion.MINECRAFT_1_5_2);
	}};
	public static ProtocolVersion getVersion(EntityPlayer player) {
		ProtocolVersion version = uuidToVersion.get(player.playerConnection.networkManager.spoofedUUID);
		return version != null ? version : ProtocolVersion.MINECRAFT_1_8;
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
