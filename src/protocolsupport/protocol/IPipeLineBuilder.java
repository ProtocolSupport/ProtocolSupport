package protocolsupport.protocol;

import protocolsupport.api.ProtocolVersion;
import io.netty.channel.Channel;

public interface IPipeLineBuilder {

	public void buildPipeLine(Channel channel, ProtocolVersion version);

}
