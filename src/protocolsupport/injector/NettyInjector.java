package protocolsupport.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.ServerConnection;
import protocolsupport.protocol.ServerConnectionChannel;
import protocolsupport.utils.Utils;

public class NettyInjector {

	@SuppressWarnings("unchecked")
	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection serverConnection = MinecraftServer.getServer().getServerConnection();
		List<NetworkManager> networkManagersList = ((List<NetworkManager>) Utils.<Field>setAccessible(serverConnection.getClass().getDeclaredField("g")).get(serverConnection));
		Channel channel = ((List<ChannelFuture>) Utils.<Field>setAccessible(serverConnection.getClass().getDeclaredField("f")).get(serverConnection)).get(0).channel();
		ChannelHandler serverHandler = channel.pipeline().first();
		Utils.<Field>setAccessible(serverHandler.getClass().getDeclaredField("childHandler")).set(serverHandler, new ServerConnectionChannel(networkManagersList));
	}

}
