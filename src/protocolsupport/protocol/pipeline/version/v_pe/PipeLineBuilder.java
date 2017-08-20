package protocolsupport.protocol.pipeline.version.v_pe;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.api.Connection;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildCodec(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManagerWrapper networkmanager = ServerPlatform.get().getMiscUtils().getNetworkManagerFromChannel(channel);
		networkmanager.setPacketListener(ServerPlatform.get().getWrapperFactory().createLegacyHandshakeListener(networkmanager));
		NetworkDataCache sharedstorage = new NetworkDataCache();
		pipeline.addAfter(ServerPlatform.get().getMiscUtils().getSplitterHandlerName(), ChannelHandlers.DECODER_TRANSFORMER, new PEPacketDecoder(connection, sharedstorage));
		pipeline.addAfter(ServerPlatform.get().getMiscUtils().getPrependerHandlerName(), ChannelHandlers.ENCODER_TRANSFORMER, new PEPacketEncoder(connection, sharedstorage));
	}

}
