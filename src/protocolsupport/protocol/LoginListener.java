package protocolsupport.protocol;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.PacketLoginOutSuccess;
import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.MinecraftServer;

import protocolsupport.injector.Utilities;

public class LoginListener extends net.minecraft.server.v1_8_R1.LoginListener {
    
    public LoginListener(final MinecraftServer minecraftserver, final NetworkManager networkmanager) {
        super(minecraftserver, networkmanager);
    }

    @Override
    public void b() {
    	try {
	    	if (ServerConnectionChannel.getVersion(Utilities.getChannel(this.networkManager).remoteAddress()) == ServerConnectionChannel.CLIENT_1_8_PROTOCOL_VERSION) {
	    		super.b();
	    	} else {
        		GameProfile profile = (GameProfile) Utilities.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("i")).get(this);
            	final EntityPlayer s = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, this.hostname);
            	if (s != null) {
	        		Class<?> clazz = Class.forName("net.minecraft.server.v1_8_R1.EnumProtocolState");
	        		Object obj = Utilities.<Field>setAccessible(clazz.getDeclaredField("ACCEPTED")).get(null);
	        		Utilities.<Field>setAccessible(net.minecraft.server.v1_8_R1.LoginListener.class.getDeclaredField("g")).set(this, obj);
	                this.networkManager.handle(new PacketLoginOutSuccess(profile));
	                MinecraftServer.getServer().getPlayerList().a(this.networkManager,  MinecraftServer.getServer().getPlayerList().processLogin(profile, s));
            	}
	    	}
    	} catch (Throwable t) {
    		t.printStackTrace();
    		this.disconnect("Failed to setup channel");
    	}
    }

}