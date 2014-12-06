package protocolsupport.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.SocketAddress;
import java.util.List;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.LegacyPingHandler;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketPrepender;
import net.minecraft.server.v1_8_R1.PacketSplitter;
import net.minecraft.server.v1_8_R1.ServerConnection;

public class ServerConnectionChannel extends ChannelInitializer<Channel> {

	private final static WeakHashMap<SocketAddress, ChannelInfo> channelInfo = new WeakHashMap<SocketAddress, ChannelInfo>() {
		@Override
		public ChannelInfo get(Object address) {
			if (!super.containsKey(address)) {
				super.put((SocketAddress) address, new ChannelInfo());
			}
			return super.get(address);
		}
	};

	public static final int CLIENT_1_8_PROTOCOL_VERSION = 47;

	public static void setVersion(SocketAddress address, int version) {
		channelInfo.get(address).version = version;
	}

	public static int getVersion(SocketAddress address) {
		return channelInfo.get(address).version;
	}

	public static void setPlayer(SocketAddress address, Player player) {
		channelInfo.get(address).player = player;
	}

	@SuppressWarnings("deprecation")
	public static Player getPlayer(SocketAddress address) {
		Player player = channelInfo.get(address).player;
		if (player == null) {
			for (Player oplayer : Bukkit.getOnlinePlayers()) {
				if (oplayer.getAddress().equals(address)) {
					return oplayer;
				}
			}
		}
		return player;
	}

	private static class ChannelInfo {
		int version = 47;
		Player player;
	}



	private ServerConnection connection;
	private List<NetworkManager> networkManagers;

	public ServerConnectionChannel(ServerConnection connection, List<NetworkManager> networkManagers) {
		this.connection = connection;
		this.networkManagers = networkManagers;
	}

	@Override
	protected void initChannel(Channel channel) {
		try {
			channel.config().setOption(ChannelOption.IP_TOS, 24);
		} catch (ChannelException channelexception) {
		}
		try {
			channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
		} catch (ChannelException channelexception1) {
		}
		channel.pipeline()
		.addLast("timeout", new ReadTimeoutHandler(30))
		.addLast("legacy_query", new LegacyPingHandler(connection))
		.addLast("splitter", new PacketSplitter())
		.addLast("decoder", new PacketDecoder())
		.addLast("prepender", new PacketPrepender())
		.addLast("encoder", new PacketEncoder());
		NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
		networkManagers.add(networkmanager);
		channel.pipeline().addLast("packet_handler", networkmanager);
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
	}

}
