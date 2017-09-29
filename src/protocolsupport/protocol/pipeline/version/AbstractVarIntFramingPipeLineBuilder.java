package protocolsupport.protocol.pipeline.version;

import io.netty.channel.Channel;
import protocolsupport.api.Connection;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractVarIntFramingPipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ServerPlatform.get().getMiscUtils().setFraming(channel.pipeline(), new VarIntFrameDecoder(), new VarIntFrameEncoder());
	}

}
