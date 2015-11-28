package protocolsupport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import protocolsupport.protocol.core.ChannelHandlers;

public class Utils {

	public static String clampString(String string, int limit) {
		return string.substring(0, string.length() > limit ? limit : string.length());
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

	public static MethodHandle getFieldSetter(Class<?> classIn, String fieldName) {
		try {
			return MethodHandles
					.lookup()
					.unreflectSetter(setAccessible(classIn.getDeclaredField(fieldName)));
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
		return null;
	}

	public static MethodHandle getFieldGetter(Class<?> classIn, String fieldName) {
		try {
			return MethodHandles
					.lookup()
					.unreflectGetter(setAccessible(classIn.getDeclaredField(fieldName)));
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
		return null;
	}

	public static Player getBukkitPlayer(Channel channel) {
		return getPlayer(getNetworkManager(channel)).getBukkitEntity();
	}

	public static EntityPlayer getPlayer(NetworkManager networkManager) {
		return ((PlayerConnection) networkManager.getPacketListener()).player;
	}

	public static SocketAddress getNetworkManagerSocketAddress(Channel channel) {
		return getNetworkManager(channel).getSocketAddress();
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

	@SuppressWarnings("unchecked")
	public static <T> T[] reverseArray(T[] array) {
		int length = array.length;
		T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length);
		for (int i = 0; i < length; i++) {
			newArray[i] = array[length - 1 - i];
		}
		return newArray;
	}

	public static int divideAndCeilWithBase(int number, int base) {
		int fp = number / base;
		int m = number % base;
		if (m == 0) {
			return fp;
		} else {
			return fp + 1;
		}
	}

}
