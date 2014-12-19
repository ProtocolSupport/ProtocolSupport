package protocolsupport.protocol.v_1_6;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.Validate;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R1.PacketLoginOutSuccess;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import protocolsupport.utils.Utils;

public class LoginListener extends net.minecraft.server.v1_8_R1.LoginListener {

	private AtomicInteger counter = new AtomicInteger(0);

	public LoginListener(final MinecraftServer minecraftserver, final NetworkManager networkmanager) {
		super(minecraftserver, networkmanager);
	}

	@Override
	public void b() {
		try {
			GameProfile profile = (GameProfile) Utils.<Field> setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("i")).get(this);
			final EntityPlayer s = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, this.hostname);
			if (s != null) {
				setCurrentState("ACCEPTED");
				this.networkManager.handle(new PacketLoginOutSuccess(profile));
				MinecraftServer.getServer().getPlayerList().a(this.networkManager, MinecraftServer.getServer().getPlayerList().processLogin(profile, s));
			}
		} catch (Throwable t) {
			t.printStackTrace();
			this.disconnect("Failed to setup channel");
		}
	}

	@Override
	public void a(final PacketLoginInEncryptionBegin packet) {
		try {
			Validate.validState(getCurrentState().equals("KEY"), "Unexpected key packet", new Object[0]);
			final PrivateKey privatekey = MinecraftServer.getServer().P().getPrivate();
			if (!Arrays.equals((byte[]) Utils.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("e")).get(this), packet.b(privatekey))) {
				throw new IllegalStateException("Invalid nonce!");
			}
			Utils.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("loginKey")).set(this, packet.a(privatekey));
			setCurrentState("AUTHENTICATING");
			((Thread) Utils.<Constructor<?>>setAccessible(
				Class.forName("net.minecraft.server.v1_8_R1.ThreadPlayerLookupUUID").getDeclaredConstructor(
						net.minecraft.server.v1_8_R1.LoginListener.class, String.class
				)
			).newInstance(this, "User Authenticator #" + counter.incrementAndGet())).start();
		} catch (Throwable t) {
			t.printStackTrace();
			this.disconnect("Failed to setup channel");
		}
	}

	private void setCurrentState(String state) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> clazz = Class.forName("net.minecraft.server.v1_8_R1.EnumProtocolState");
		Object obj = Utils.<Field>setAccessible(clazz.getDeclaredField(state)).get(null);
		Utils.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("g")).set(this, obj);
	}

	private String getCurrentState() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return Utils.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("g")).get(this).toString();
	}

}