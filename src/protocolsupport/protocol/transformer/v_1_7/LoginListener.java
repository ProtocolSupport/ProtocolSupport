package protocolsupport.protocol.transformer.v_1_7;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;

import protocolsupport.protocol.transformer.AbstractLoginListener;
import protocolsupport.protocol.transformer.LoginState;

public class LoginListener extends AbstractLoginListener {

	public LoginListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@Override
	public void completeLogin() {
		final EntityPlayer s = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, hostname);
		if (s != null) {
			state = LoginState.ACCEPTED;
			networkManager.handle(new PacketLoginOutSuccess(profile));
			MinecraftServer.getServer().getPlayerList().a(networkManager, MinecraftServer.getServer().getPlayerList().processLogin(profile, s));
		}
	}

}