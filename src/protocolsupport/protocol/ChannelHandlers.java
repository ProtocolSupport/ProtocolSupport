package protocolsupport.protocol;

import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.wrapped.WrappedDecoder;
import protocolsupport.protocol.wrapped.WrappedEncoder;

public class ChannelHandlers {

	public static final String INITIAL_DECODER = "initial_decoder";
	public static final String SPLITTER = "splitter";
	public static final String PREPENDER = "prepender";
	public static final String DECODER = "decoder";
	public static final String ENCODER = "encoder";
	public static final String NETWORK_MANAGER = "packet_handler";

	public static WrappedDecoder getDecoder(ChannelPipeline pipeline) {
		return (WrappedDecoder) pipeline.get(DECODER);
	}

	public static WrappedEncoder getEncoder(ChannelPipeline pipeline) {
		return (WrappedEncoder) pipeline.get(ENCODER);
	}

}
