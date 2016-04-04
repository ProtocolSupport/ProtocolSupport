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
import net.minecraft.server.v1_9_R1.LazyInitVar;
import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.minecraft.server.v1_9_R1.NetworkManager;
import net.minecraft.server.v1_9_R1.ServerConnection;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.core.UDPServerConnectionChannel;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public class NettyInjector {

	private static final boolean useNonBlockingServerConnection = Utils.getJavaPropertyValue("protocolsupport.nonblockingconection", false, Converter.STRING_TO_BOOLEAN);

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection connection = MinecraftServer.getServer().getServerConnection();
		if (MinecraftServer.getServer().ae()) {
			ProtocolSupport.logWarning("Native transport is enabled, this may causes issues. Disable it by setting use-native-transport in server.properties to false.");
		}
		if (connection == null && useNonBlockingServerConnection) {
			NonBlockingServerConnection.inject();
			ProtocolSupport.logInfo("Using NonBlockingServerConnection");
		} else {
			BasicInjector.inject();
			ProtocolSupport.logInfo("Using injected ServerConnection");
		}
		ServerConnection serverConnection = MinecraftServer.getServer().am();
		startUDP(((List<NetworkManager>) ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("h")).get(serverConnection)));
	}

	private static ChannelFuture UDP_CONTROL;

	public static ChannelFuture getUDP() {
		return UDP_CONTROL;
	}

	private static final LazyInitVar<EventLoopGroup> loopGroup = new LazyInitVar<EventLoopGroup>() {
		@SuppressWarnings("deprecation")
		@Override
		protected EventLoopGroup init() {
			return Epoll.isAvailable() && MinecraftServer.getServer().ae() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		}
	};

	private static final LazyInitVar<Class<? extends DatagramChannel>> channel = new LazyInitVar<Class<? extends DatagramChannel>>() {
		@SuppressWarnings("deprecation")
		@Override
		protected Class<? extends DatagramChannel> init() {
			return Epoll.isAvailable() && MinecraftServer.getServer().ae() ? EpollDatagramChannel.class : NioDatagramChannel.class;
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
