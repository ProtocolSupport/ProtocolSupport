package protocolsupport.injector.network;

import java.util.List;

import org.bukkit.Bukkit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import net.minecraft.server.v1_8_R3.LazyInitVar;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.ServerConnection;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.core.UDPServerConnectionChannel;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public class NettyInjector {

	private static final boolean useNonBlockingServerConnection = Utils.getJavaPropertyValue("protocolsupport.nonblockingconection", false, Converter.STRING_TO_BOOLEAN);

	@SuppressWarnings("unchecked")
	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection connection = MinecraftServer.getServer().getServerConnection();
		if (connection == null && useNonBlockingServerConnection) {
			NonBlockingServerConnection.inject();
			ProtocolSupport.logInfo("Using NonBlockingServerConnection");
		} else {
			BasicInjector.inject();
			ProtocolSupport.logInfo("Using injected ServerConnection");
		}
		ServerConnection serverConnection = MinecraftServer.getServer().aq();
		startUDP(((List<NetworkManager>) Utils.setAccessible(ServerConnection.class.getDeclaredField("h")).get(serverConnection)));
	}

	private static ChannelFuture UDP_CONTROL;

	public static ChannelFuture getUDP() {
		return UDP_CONTROL;
	}

	private static final LazyInitVar<EventLoopGroup> loopGroup = new LazyInitVar<EventLoopGroup>() {
		@Override
		protected EventLoopGroup init() {
			return Epoll.isAvailable() && MinecraftServer.getServer().ai() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		}
	};

	private static final LazyInitVar<Class<? extends DatagramChannel>> channel = new LazyInitVar<Class<? extends DatagramChannel>>() {
		@Override
		protected Class<? extends DatagramChannel> init() {
			return Epoll.isAvailable() && MinecraftServer.getServer().ai() ? EpollDatagramChannel.class : NioDatagramChannel.class;
		}
	};

	public static void startUDP(List<NetworkManager> managers) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(loopGroup.c()).channel(channel.c()).handler(new UDPServerConnectionChannel(managers));
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
