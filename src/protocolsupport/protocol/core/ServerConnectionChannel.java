package protocolsupport.protocol.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.List;

import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import protocolsupport.protocol.core.initial.InitialPacketDecoder;
import protocolsupport.protocol.core.wrapped.WrappedDecoder;
import protocolsupport.protocol.core.wrapped.WrappedEncoder;
import protocolsupport.protocol.core.wrapped.WrappedPrepender;
import protocolsupport.protocol.core.wrapped.WrappedSplitter;

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
			channel.config().setOption(ChannelOption.TCP_NODELAY, false);
		} catch (ChannelException channelexception) {
		}
		channel.pipeline()
		.addLast("timeout", new ReadTimeoutHandler(30))
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
