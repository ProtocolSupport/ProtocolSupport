package protocolsupport.protocol.pipeline.version.util.builder;

import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.IPipelineBuilder;
import protocolsupport.protocol.pipeline.common.NoOpFrameDecoder;
import protocolsupport.protocol.pipeline.common.NoOpFrameEncoder;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractNoopFramingPipelineBuilder implements IPipelineBuilder {

	@Override
	public void buildTransport(ChannelPipeline pipeline) {
		ServerPlatform.get().getMiscUtils().setFraming(pipeline, new NoOpFrameDecoder(), new NoOpFrameEncoder());
	}

}
