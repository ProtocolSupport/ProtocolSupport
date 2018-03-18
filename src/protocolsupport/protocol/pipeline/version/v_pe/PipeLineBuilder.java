package protocolsupport.protocol.pipeline.version.v_pe;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.api.Connection;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.packet.PEDimensionSwitchMovementConfirmationPacketQueue;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildCodec(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkDataCache sharedstorage = new NetworkDataCache();
		PEDimensionSwitchMovementConfirmationPacketQueue dimswitchq = new PEDimensionSwitchMovementConfirmationPacketQueue();
		pipeline.addAfter(ChannelHandlers.RAW_CAPTURE_RECEIVE, ChannelHandlers.DECODER_TRANSFORMER, new PEPacketDecoder(connection, sharedstorage, dimswitchq));
		pipeline.addAfter(ChannelHandlers.RAW_CAPTURE_SEND, ChannelHandlers.ENCODER_TRANSFORMER, new PEPacketEncoder(connection, sharedstorage, dimswitchq));
	}

}
