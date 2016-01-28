package protocolsupport.injector.network;

import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.ServerConnection;
import protocolsupport.utils.Utils;

public class BasicInjector {

	@SuppressWarnings("unchecked")
	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection serverConnection = MinecraftServer.getServer().getServerConnection();
		List<NetworkManager> networkManagersList = ((List<NetworkManager>) Utils.setAccessible(serverConnection.getClass().getDeclaredField("h")).get(serverConnection));
		Channel channel = ((List<ChannelFuture>) Utils.setAccessible(serverConnection.getClass().getDeclaredField("g")).get(serverConnection)).get(0).channel();
		ChannelHandler serverHandler = channel.pipeline().first();
		Utils.setAccessible(serverHandler.getClass().getDeclaredField("childHandler")).set(serverHandler, new ServerConnectionChannel(networkManagersList));
		synchronized (networkManagersList) {
			for (NetworkManager nm : networkManagersList) {
				nm.close(new ChatComponentText("ProtocolSupport channel reset"));
			}
		}
	}

}
