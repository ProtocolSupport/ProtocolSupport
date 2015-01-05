package protocolsupport.protocol.v_1_7;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketLoginOutSuccess;
import protocolsupport.utils.Utils;

import com.mojang.authlib.GameProfile;

public class LoginListener extends net.minecraft.server.v1_8_R1.LoginListener {

	public LoginListener(final MinecraftServer minecraftserver, final NetworkManager networkmanager) {
		super(minecraftserver, networkmanager);
	}

	@Override
	public void b() {
		try {
			GameProfile profile = (GameProfile) Utils.setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("i")).get(this);
			final EntityPlayer s = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, hostname);
			if (s != null) {
				Class<?> clazz = Class.forName("net.minecraft.server.v1_8_R1.EnumProtocolState");
				Object obj = Utils.<Field> setAccessible(clazz.getDeclaredField("ACCEPTED")).get(null);
				Utils.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("g")).set(this, obj);
				networkManager.handle(new PacketLoginOutSuccess(profile));
				MinecraftServer.getServer().getPlayerList().a(networkManager, MinecraftServer.getServer().getPlayerList().processLogin(profile, s));
			}
		} catch (Throwable t) {
			t.printStackTrace();
			disconnect("Failed to setup channel");
		}
	}

}