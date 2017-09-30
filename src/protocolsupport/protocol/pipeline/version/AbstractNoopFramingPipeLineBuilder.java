package protocolsupport.protocol.pipeline.version;

import io.netty.channel.Channel;
import protocolsupport.api.Connection;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.NoOpFrameDecoder;
import protocolsupport.protocol.pipeline.common.NoOpFrameEncoder;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractNoopFramingPipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ServerPlatform.get().getMiscUtils().setFraming(channel.pipeline(), new NoOpFrameDecoder(), new NoOpFrameEncoder());
	}

}
