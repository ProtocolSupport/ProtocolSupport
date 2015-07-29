package protocolsupport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.entity.Player;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.ChannelHandlers;

public class Utils {

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
	}

	public static void writeAll(PacketDataSerializer input, PacketDataSerializer output) {
		output.writeBytes(input);
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
		return ((PlayerConnection) getNetworkManager(channel).getPacketListener()).player.getBukkitEntity();
	}

	public static SocketAddress getNetworkManagerSocketAddress(Channel channel) {
		return getNetworkManager(channel).l;
	}

	public static NetworkManager getNetworkManager(Channel channel) {
		return (NetworkManager) channel.pipeline().get(ChannelHandlers.NETWORK_MANAGER);
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

	public static byte[] toArray(ByteBuf buf) {
		byte[] result = new byte[buf.readableBytes()];
		buf.readBytes(result);
		return result;
	}

}
