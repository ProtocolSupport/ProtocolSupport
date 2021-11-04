package protocolsupport.protocol.pipeline.version.v_f;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.protocol.pipeline.version.util.builder.AbstractVarIntFramingPipelineBuilder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PipelineBuilder extends AbstractVarIntFramingPipelineBuilder {

	@Override
	public IPacketIdCodec getPacketIdCodec() {
		return IPacketIdCodec.LATEST;
	}

	@Override
	public void buildCodec(ChannelPipeline pipeline, IPacketDataChannelIO io, NetworkDataCache cache) {
		pipeline
		.addAfter(ChannelHandlers.RAW_CAPTURE_RECEIVE, ChannelHandlers.DECODER_TRANSFORMER, new ChannelInboundHandlerAdapter())
		.addAfter(ChannelHandlers.RAW_CAPTURE_SEND, ChannelHandlers.ENCODER_TRANSFORMER, new ChannelOutboundHandlerAdapter());
	}

}
