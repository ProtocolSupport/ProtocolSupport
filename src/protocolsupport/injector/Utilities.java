package protocolsupport.injector;

import io.netty.channel.Channel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R1.NetworkManager;

public class Utilities {

	@SuppressWarnings("unchecked")
	public static <T> T setAccessible(AccessibleObject object) {
		object.setAccessible(true);
		return (T) object;
	}

	public static Channel getChannel(NetworkManager nm) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return (Channel) Utilities.<Field>setAccessible(NetworkManager.class.getDeclaredField("i")).get(nm);
	}

}
