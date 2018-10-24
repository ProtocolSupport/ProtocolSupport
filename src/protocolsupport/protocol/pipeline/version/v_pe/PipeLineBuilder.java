package protocolsupport.protocol.pipeline.version.v_pe;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.typeremapper.packet.PEDimensionSwitchMovementConfirmationPacketQueue;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, ConnectionImpl connection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void buildCodec(Channel channel, ConnectionImpl connection) {
		ChannelPipeline pipeline = channel.pipeline();
		PEDimensionSwitchMovementConfirmationPacketQueue dimswitchq = new PEDimensionSwitchMovementConfirmationPacketQueue();
		pipeline.addAfter(ChannelHandlers.RAW_CAPTURE_RECEIVE, ChannelHandlers.DECODER_TRANSFORMER, new PEPacketDecoder(connection, dimswitchq));
		pipeline.addAfter(ChannelHandlers.RAW_CAPTURE_SEND, ChannelHandlers.ENCODER_TRANSFORMER, new PEPacketEncoder(connection, dimswitchq));
	}

}
