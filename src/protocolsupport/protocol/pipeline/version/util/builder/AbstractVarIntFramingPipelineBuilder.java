package protocolsupport.protocol.pipeline.version.util.builder;

import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.IPipelineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractVarIntFramingPipelineBuilder implements IPipelineBuilder {

	@Override
	public void buildTransport(ChannelPipeline pipeline) {
		ServerPlatform.get().getMiscUtils().setFraming(pipeline, new VarIntFrameDecoder(), new VarIntFrameEncoder());
	}

}
