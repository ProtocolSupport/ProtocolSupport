package protocolsupport.protocol.pipeline;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.List;

import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.NetworkManager;
import protocolsupport.protocol.pipeline.fake.FakePacketListener;
import protocolsupport.protocol.pipeline.fake.FakePrepender;
import protocolsupport.protocol.pipeline.fake.FakeSplitter;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.pipeline.wrapped.WrappedDecoder;
import protocolsupport.protocol.pipeline.wrapped.WrappedEncoder;

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
		.addLast(ChannelHandlers.SPLITTER, new FakeSplitter())
		.addLast(ChannelHandlers.DECODER, new WrappedDecoder())
		.addLast(ChannelHandlers.PREPENDER, new FakePrepender())
		.addLast(ChannelHandlers.ENCODER, new WrappedEncoder());
		NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
		networkmanager.a(new FakePacketListener());
		networkManagers.add(networkmanager);
		channel.pipeline().addLast(ChannelHandlers.NETWORK_MANAGER, networkmanager);
	}

}
