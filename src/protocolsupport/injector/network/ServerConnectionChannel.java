package protocolsupport.injector.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.FakePacketListener;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.pipeline.common.PacketDecoder;
import protocolsupport.protocol.pipeline.common.PacketEncoder;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.pipeline.timeout.SimpleReadTimeoutHandler;
import protocolsupport.protocol.pipeline.wrapped.WrappedPrepender;
import protocolsupport.protocol.pipeline.wrapped.WrappedSplitter;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.ServerPlatformUtils;

public class ServerConnectionChannel extends ChannelInitializer<Channel> {

	private final NetworkManagerList networkManagers;
	public ServerConnectionChannel(NetworkManagerList networkManagers) {
		this.networkManagers = networkManagers;
	}

	private static final int IPTOS_THROUGHPUT = 0x08;
	private static final int IPTOS_LOWDELAY = 0x10;

	@Override
	protected void initChannel(Channel channel) {
		try {
			channel.config().setOption(ChannelOption.IP_TOS, IPTOS_THROUGHPUT | IPTOS_LOWDELAY);
		} catch (ChannelException channelexception) {
			if (ServerPlatformUtils.getServer().isDebugging()) {
				System.err.println("Unable to set IP_TOS option: " + channelexception.getMessage());
			}
		}
		try {
			channel.config().setOption(ChannelOption.TCP_NODELAY, true);
		} catch (ChannelException channelexception) {
			if (ServerPlatformUtils.getServer().isDebugging()) {
				System.err.println("Unable to set TCP_NODELAY option: " + channelexception.getMessage());
			}
		}
		NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
		ConnectionImpl connection = new ConnectionImpl(networkmanager, ProtocolVersion.UNKNOWN);
		ProtocolStorage.setConnection(channel.remoteAddress(), connection);
		channel.pipeline()
		.addLast(ChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30))
		.addLast(ChannelHandlers.INITIAL_DECODER, new InitialPacketDecoder())
		.addLast(ChannelHandlers.SPLITTER, new WrappedSplitter())
		.addLast(ChannelHandlers.DECODER, new PacketDecoder())
		.addLast(ChannelHandlers.PREPENDER, new WrappedPrepender())
		.addLast(ChannelHandlers.ENCODER, new PacketEncoder())
		.addLast(ChannelHandlers.LOGIC, new LogicHandler(connection));
		networkmanager.setPacketListener(new FakePacketListener());
		networkManagers.add(networkmanager);
		channel.pipeline().addLast(ChannelHandlers.NETWORK_MANAGER, networkmanager);
	}

}
