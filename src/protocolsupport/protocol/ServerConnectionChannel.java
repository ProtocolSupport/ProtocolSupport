package protocolsupport.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.List;

import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.LegacyPingHandler;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketPrepender;
import net.minecraft.server.v1_8_R1.PacketSplitter;
import net.minecraft.server.v1_8_R1.ServerConnection;

public class ServerConnectionChannel extends ChannelInitializer<Channel> {

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
