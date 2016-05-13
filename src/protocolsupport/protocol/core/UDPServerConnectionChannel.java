package protocolsupport.protocol.core;

import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.minecraft.server.v1_9_R2.NetworkManager;
import protocolsupport.protocol.packet.mcpe.pipeline.RakNetDecoder;
import protocolsupport.protocol.packet.mcpe.pipeline.RakNetEncoder;
import protocolsupport.protocol.packet.mcpe.pipeline.UDPRouter;

public class UDPServerConnectionChannel extends ChannelInitializer<Channel> {

	private List<NetworkManager> networkManagers;

	public UDPServerConnectionChannel(List<NetworkManager> networkManagers) {
		this.networkManagers = networkManagers;
	}

	@Override
	protected void initChannel(Channel channel) throws Exception {
		channel.pipeline()
		.addLast("raknetdecoder", new RakNetDecoder())
		.addLast("raknetencoder", new RakNetEncoder())
		.addLast("udprouter", UDPRouter.init(networkManagers));
		channel.config().setAutoRead(true);
	}

}
