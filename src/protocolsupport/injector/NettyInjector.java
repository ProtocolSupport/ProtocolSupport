package protocolsupport.injector;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.util.List;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.ServerConnection;
import protocolsupport.protocol.core.ServerConnectionChannel;
import protocolsupport.protocol.core.UDPServerConnectionChannel;
import protocolsupport.utils.Utils;

public class NettyInjector {

	private static ChannelFuture UDP_CONTROL;

	public static ChannelFuture getUDP() {
		return UDP_CONTROL;
	}

	@SuppressWarnings("unchecked")
	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection serverConnection = MinecraftServer.getServer().getServerConnection();
		List<NetworkManager> networkManagersList = ((List<NetworkManager>) Utils.setAccessible(serverConnection.getClass().getDeclaredField("h")).get(serverConnection));
		List<ChannelFuture> channelFutureList = ((List<ChannelFuture>) Utils.setAccessible(serverConnection.getClass().getDeclaredField("g")).get(serverConnection));
		Channel channel = channelFutureList.get(0).channel();
		ChannelHandler serverHandler = channel.pipeline().first();
		Utils.setAccessible(serverHandler.getClass().getDeclaredField("childHandler")).set(serverHandler, new ServerConnectionChannel(networkManagersList));
		startUDP(networkManagersList);
	}

	public static void startUDP(List<NetworkManager> managers) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap
		.group(new NioEventLoopGroup())
		.channel(NioDatagramChannel.class).handler(new UDPServerConnectionChannel(managers));
		UDP_CONTROL = bootstrap.bind(getIP(), Bukkit.getPort()).syncUninterruptibly();
	}

	private static String getIP() {
		String bukkitip = Bukkit.getIp();
		if (bukkitip == null || bukkitip.isEmpty()) {
			return "0.0.0.0";
		}
		return bukkitip;
	}

}
