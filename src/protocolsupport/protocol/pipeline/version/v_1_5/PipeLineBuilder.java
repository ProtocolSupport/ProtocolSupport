package protocolsupport.protocol.pipeline.version.v_1_5;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.handler.common.LegacyHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.NoOpFrameDecoder;
import protocolsupport.protocol.pipeline.common.NoOpFrameEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.nms.NetworkManagerWrapper;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManagerWrapper networkmanager = NetworkManagerWrapper.getFromChannel(channel);
		networkmanager.setPacketListener(new LegacyHandshakeListener(networkmanager));
		ChannelHandlers.getSplitter(pipeline).setRealSplitter(new NoOpFrameDecoder());
		ChannelHandlers.getPrepender(pipeline).setRealPrepender(new NoOpFrameEncoder());
		NetworkDataCache sharedstorage = new NetworkDataCache();
		pipeline.addAfter(ChannelHandlers.SPLITTER, ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(connection, sharedstorage));
		pipeline.addAfter(ChannelHandlers.PREPENDER, ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(connection, sharedstorage));
	}

}
