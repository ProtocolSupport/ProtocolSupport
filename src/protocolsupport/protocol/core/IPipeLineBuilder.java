package protocolsupport.protocol.core;

import io.netty.channel.Channel;
import protocolsupport.api.ProtocolVersion;

public interface IPipeLineBuilder {

	public void buildPipeLine(Channel channel, ProtocolVersion version);

}
