package protocolsupport.protocol.pipeline.version.v_1_5;

import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.protocol.pipeline.version.util.builder.AbstractNoopFramingPipelineBuilder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.packet.AnimatePacketReorderer;

public class PipelineBuilder extends AbstractNoopFramingPipelineBuilder {

	@Override
	public IPacketIdCodec getPacketIdCodec() {
		return PacketCodec.instance;
	}

	@Override
	public void buildCodec(ChannelPipeline pipeline, IPacketDataChannelIO io, NetworkDataCache cache) {
		pipeline
		.addAfter(ChannelHandlers.RAW_CAPTURE_RECEIVE, ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(io, cache))
		.addAfter(ChannelHandlers.RAW_CAPTURE_SEND, ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(io, cache));
	}

	@Override
	public void buildPostProcessor(IPacketDataChannelIO io) {
		io.addProcessor(new AnimatePacketReorderer());
	}

}
