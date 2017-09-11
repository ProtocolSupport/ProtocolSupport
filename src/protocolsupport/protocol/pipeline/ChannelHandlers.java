package protocolsupport.protocol.pipeline;

import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.zplatform.ServerPlatform;

public class ChannelHandlers {

	public static final String INITIAL_DECODER = "ps_initial_decoder";
	public static final String DECODER_TRANSFORMER = "ps_decoder_transformer";
	public static final String ENCODER_TRANSFORMER = "ps_encoder_transformer";
	public static final String LOGIC = "ps_logic";
	public static final String RAW_CAPTURE_RECEIVE = "ps_raw_capture_receive";
	public static final String RAW_CAPTURE_SEND = "ps_raw_capture_send";
	public static final String LEGACY_KICK = "ps_legacy_kick";
	public static final String ENCRYPT = "encrypt";
	public static final String DECRYPT = "decrypt";

	public static SimpleReadTimeoutHandler getTimeoutHandler(ChannelPipeline pipeline) {
		return (SimpleReadTimeoutHandler) pipeline.get(ServerPlatform.get().getMiscUtils().getReadTimeoutHandlerName());
	}

}
