package protocolsupport.protocol.pipeline.version.v_1_9.r2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.api.Connection;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ServerPlatform.get().getMiscUtils().setFraming(channel.pipeline(), new VarIntFrameDecoder(), new VarIntFrameEncoder());
	}

	@Override
	public void buildCodec(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManagerWrapper networkmanager = ServerPlatform.get().getMiscUtils().getNetworkManagerFromChannel(channel);
		networkmanager.setPacketListener(ServerPlatform.get().getWrapperFactory().createHandshakeListener(networkmanager, true, true));
		NetworkDataCache sharedstorage = new NetworkDataCache();
		pipeline.addAfter(ServerPlatform.get().getMiscUtils().getSplitterHandlerName(), ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(connection, sharedstorage));
		pipeline.addAfter(ServerPlatform.get().getMiscUtils().getPrependerHandlerName(), ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(connection, sharedstorage));
	}

}
