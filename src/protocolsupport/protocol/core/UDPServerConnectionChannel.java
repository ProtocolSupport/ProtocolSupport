package protocolsupport.protocol.core;

import java.util.List;

import protocolsupport.protocol.transformer.mcpe.pipeline.RakNetDecoder;
import protocolsupport.protocol.transformer.mcpe.pipeline.RakNetEncoder;
import protocolsupport.protocol.transformer.mcpe.pipeline.UDPRouter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.minecraft.server.v1_9_R1.NetworkManager;

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
