package protocolsupport.protocol.transformer.v_1_8;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;
import protocolsupport.protocol.transformer.AbstractLoginListener;
import protocolsupport.protocol.transformer.LoginState;

public class LoginListener extends AbstractLoginListener {

	public LoginListener(NetworkManager networkmanager) {
		super(networkmanager);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void completeLogin() {
		final EntityPlayer s = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, hostname);
		if (s != null) {
			state = LoginState.ACCEPTED;
			if (MinecraftServer.getServer().aK() >= 0 && !this.networkManager.c()) {
				this.networkManager.a(
					new PacketLoginOutSetCompression(MinecraftServer.getServer().aK()),
					new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture future) throws Exception {
							networkManager.a(MinecraftServer.getServer().aK());
						}
					}
				);
			}
			networkManager.handle(new PacketLoginOutSuccess(profile));
			MinecraftServer.getServer().getPlayerList().a(networkManager, MinecraftServer.getServer().getPlayerList().processLogin(profile, s));
		}
	}

}