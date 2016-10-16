package protocolsupport.protocol.pipeline;

import io.netty.channel.Channel;
import protocolsupport.api.unsafe.Connection;

public interface IPipeLineBuilder {

	public void buildPipeLine(Channel channel, Connection connection);

}
