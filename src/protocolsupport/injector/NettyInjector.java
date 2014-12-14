package protocolsupport.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;

import java.lang.reflect.Field;
import java.util.List;

import protocolsupport.protocol.ServerConnectionChannel;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.ServerConnection;

public class NettyInjector {

	@SuppressWarnings("unchecked")
	public static void injectStreamSerializer() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection serverConnection = MinecraftServer.getServer().getServerConnection();
		List<NetworkManager> networkManagersList = ((List<NetworkManager>) Utilities.<Field>setAccessible(serverConnection.getClass().getDeclaredField("g")).get(serverConnection));
		Channel channel = ((List<ChannelFuture>) Utilities.<Field>setAccessible(serverConnection.getClass().getDeclaredField("f")).get(serverConnection)).get(0).channel();
		ChannelHandler serverHandler = channel.pipeline().first();
		Utilities.<Field>setAccessible(serverHandler.getClass().getDeclaredField("childHandler")).set(serverHandler, new ServerConnectionChannel(networkManagersList));
	}

}
