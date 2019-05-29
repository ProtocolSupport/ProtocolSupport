package protocolsupport.protocol.pipeline.version.v_1_14;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.version.util.builder.AbstractVarIntFramingPipeLineBuilder;

public class PipeLineBuilder extends AbstractVarIntFramingPipeLineBuilder {

	@Override
	public void buildCodec(Channel channel, ConnectionImpl connection) {
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addAfter(ChannelHandlers.RAW_CAPTURE_RECEIVE, ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(connection));
		pipeline.addAfter(ChannelHandlers.RAW_CAPTURE_SEND, ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(connection));
	}

}
