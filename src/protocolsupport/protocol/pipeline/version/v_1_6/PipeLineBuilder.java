package protocolsupport.protocol.pipeline.version.v_1_6;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.api.Connection;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.NoOpFrameDecoder;
import protocolsupport.protocol.pipeline.common.NoOpFrameEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ServerPlatform.get().getMiscUtils().setFraming(channel.pipeline(), new NoOpFrameDecoder(), new NoOpFrameEncoder());
	}

	@Override
	public void buildCodec(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManagerWrapper networkmanager = ServerPlatform.get().getMiscUtils().getNetworkManagerFromChannel(channel);
		networkmanager.setPacketListener(ServerPlatform.get().getWrapperFactory().createLegacyHandshakeListener(networkmanager));
		NetworkDataCache sharedstorage = new NetworkDataCache();
		pipeline.addAfter(ServerPlatform.get().getMiscUtils().getSplitterHandlerName(), ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(connection, sharedstorage));
		pipeline.addAfter(ServerPlatform.get().getMiscUtils().getPrependerHandlerName(), ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(connection, sharedstorage));
	}

}
