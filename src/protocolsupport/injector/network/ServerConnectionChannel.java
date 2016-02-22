package protocolsupport.injector.network;

import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import protocolsupport.protocol.core.ChannelHandlers;
import protocolsupport.protocol.core.FakePacketListener;
import protocolsupport.protocol.core.initial.InitialPacketDecoder;
import protocolsupport.protocol.core.timeout.SimpleReadTimeoutHandler;
import protocolsupport.protocol.core.wrapped.WrappedDecoder;
import protocolsupport.protocol.core.wrapped.WrappedEncoder;
import protocolsupport.protocol.core.wrapped.WrappedPrepender;
import protocolsupport.protocol.core.wrapped.WrappedSplitter;

public class ServerConnectionChannel extends ChannelInitializer<Channel> {

	private final List<NetworkManager> networkManagers;
	public ServerConnectionChannel(List<NetworkManager> networkManagers) {
		this.networkManagers = networkManagers;
	}

	private static final int IPTOS_THROUGHPUT = 0x08;
	private static final int IPTOS_LOWDELAY = 0x10;

	@Override
	protected void initChannel(Channel channel) {
		try {
			channel.config().setOption(ChannelOption.IP_TOS, IPTOS_THROUGHPUT | IPTOS_LOWDELAY);
		} catch (ChannelException channelexception) {
			if (MinecraftServer.getServer().isDebugging()) {
				System.err.println("Unable to set IP_TOS option: " + channelexception.getMessage());
			}
		}
		try {
			channel.config().setOption(ChannelOption.TCP_NODELAY, true);
		} catch (ChannelException channelexception) {
			if (MinecraftServer.getServer().isDebugging()) {
				System.err.println("Unable to set TCP_NODELAY option: " + channelexception.getMessage());
			}
		}
		channel.pipeline()
		.addLast("timeout", new SimpleReadTimeoutHandler(30))
		.addLast(ChannelHandlers.INITIAL_DECODER, new InitialPacketDecoder())
		.addLast(ChannelHandlers.SPLITTER, new WrappedSplitter())
		.addLast(ChannelHandlers.DECODER, new WrappedDecoder())
		.addLast(ChannelHandlers.PREPENDER, new WrappedPrepender())
		.addLast(ChannelHandlers.ENCODER, new WrappedEncoder());
		NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
		networkmanager.a(new FakePacketListener());
		networkManagers.add(networkmanager);
		channel.pipeline().addLast(ChannelHandlers.NETWORK_MANAGER, networkmanager);
	}

}
