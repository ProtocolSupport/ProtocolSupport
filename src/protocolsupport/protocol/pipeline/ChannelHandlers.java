package protocolsupport.protocol.pipeline;

import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.timeout.SimpleReadTimeoutHandler;
import protocolsupport.protocol.pipeline.wrapped.WrappedPrepender;
import protocolsupport.protocol.pipeline.wrapped.WrappedSplitter;
import protocolsupport.zplatform.ServerPlatform;

public class ChannelHandlers {

	public static final String INITIAL_DECODER = "ps_initial_decoder";
	public static final String DECODER_TRANSFORMER = "ps_decoder_transformer";
	public static final String ENCODER_TRANSFORMER = "ps_encoder_transformer";
	public static final String LOGIC = "ps_logic";
	public static final String LEGACY_KICK = "ps_legacy_kick";

	public static WrappedSplitter getSplitter(ChannelPipeline pipeline) {
		return (WrappedSplitter) pipeline.get(ServerPlatform.get().getMiscUtils().getSplitterHandlerName());
	}
	public static WrappedPrepender getPrepender(ChannelPipeline pipeline) {
		return (WrappedPrepender) pipeline.get(ServerPlatform.get().getMiscUtils().getPrependerHandlerName());
	}
	public static SimpleReadTimeoutHandler getTimeoutHandler(ChannelPipeline pipeline) {
		return (SimpleReadTimeoutHandler) pipeline.get(ServerPlatform.get().getMiscUtils().getReadTimeoutHandlerName());
	}

}
