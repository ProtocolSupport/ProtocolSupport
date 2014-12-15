package protocolsupport.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.List;

import protocolsupport.protocol.fake.FakeDecoder;
import protocolsupport.protocol.fake.FakeEncoder;
import protocolsupport.protocol.fake.FakePrepender;
import protocolsupport.protocol.fake.FakeSplitter;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
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
			channel.config().setOption(ChannelOption.TCP_NODELAY, false);
		} catch (ChannelException channelexception) {
		}
		channel.pipeline()
		.addLast("timeout", new ReadTimeoutHandler(30))
		.addLast("initial_decoder", new InitialPacketDecoder())
		//fake elements, will be replaced or removed manually on every protocol pipeline builder
		.addLast("splitter", new FakeSplitter())
		.addLast("decoder", new FakeDecoder())
		.addLast("prepender", new FakePrepender())
		.addLast("encoder", new FakeEncoder());
		NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
		networkManagers.add(networkmanager);
		channel.pipeline().addLast("packet_handler", networkmanager);
	}

}
