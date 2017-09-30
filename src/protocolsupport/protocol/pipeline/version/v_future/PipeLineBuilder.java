package protocolsupport.protocol.pipeline.version.v_future;

import io.netty.channel.Channel;
import protocolsupport.api.Connection;
import protocolsupport.protocol.pipeline.version.AbstractVarIntFramingPipeLineBuilder;

public class PipeLineBuilder extends AbstractVarIntFramingPipeLineBuilder {

	@Override
	public void buildCodec(Channel channel, Connection connection) {
	}

}
