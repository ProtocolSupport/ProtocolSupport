package protocolsupport.protocol.pipeline;

import io.netty.channel.Channel;
import protocolsupport.protocol.ConnectionImpl;

public interface IPipeLineBuilder {

	public void buildPipeLine(Channel channel, ConnectionImpl connection);

	public void buildCodec(Channel channel, ConnectionImpl connection);

}
