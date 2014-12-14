package protocolsupport.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.List;

import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;

public class ServerConnectionChannel extends ChannelInitializer<Channel> {

	private List<NetworkManager> networkManagers;

	public ServerConnectionChannel(List<NetworkManager> networkManagers) {
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
		.addLast("decoder", new PacketDecoder())
		.addLast("encoder", new PacketEncoder());
		NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
		networkManagers.add(networkmanager);
		channel.pipeline().addLast("packet_handler", networkmanager);
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
	}

}
