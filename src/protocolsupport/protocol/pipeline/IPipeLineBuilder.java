package protocolsupport.protocol.pipeline;

import io.netty.channel.Channel;
import protocolsupport.api.ProtocolVersion;

public interface IPipeLineBuilder {

	public void buildPipeLine(Channel channel, ProtocolVersion version);

}
