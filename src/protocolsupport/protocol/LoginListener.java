package protocolsupport.protocol;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R1.PacketLoginOutSuccess;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import protocolsupport.injector.Utilities;
import protocolsupport.protocol.DataStorage.ProtocolVersion;

public class LoginListener extends net.minecraft.server.v1_8_R1.LoginListener {
    
    public LoginListener(final MinecraftServer minecraftserver, final NetworkManager networkmanager) {
        super(minecraftserver, networkmanager);
    }

    @Override
    public void b() {
    	try {
    		GameProfile profile = (GameProfile) Utilities.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("i")).get(this);
        	final EntityPlayer s = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, this.hostname);
        	if (s != null) {
        		Class<?> clazz = Class.forName("net.minecraft.server.v1_8_R1.EnumProtocolState");
        		Object obj = Utilities.<Field>setAccessible(clazz.getDeclaredField("ACCEPTED")).get(null);
        		Utilities.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("g")).set(this, obj);
        		if (DataStorage.getVersion(this.networkManager.getRawAddress()) == ProtocolVersion.MINECRAFT_1_8) {
        			this.networkManager.a(new PacketLoginOutSetCompression(MinecraftServer.getServer().aI()), new GenericFutureListener<Future<?>>() {
						@Override
						public void operationComplete(Future<?> future) throws Exception {
							DataStorage.setCompressionEnabled(LoginListener.this.networkManager.getRawAddress());;
						}
					});
        		}
                this.networkManager.handle(new PacketLoginOutSuccess(profile));
                MinecraftServer.getServer().getPlayerList().a(this.networkManager,  MinecraftServer.getServer().getPlayerList().processLogin(profile, s));
        	}
    	} catch (Throwable t) {
    		t.printStackTrace();
    		this.disconnect("Failed to setup channel");
    	}
    }

}