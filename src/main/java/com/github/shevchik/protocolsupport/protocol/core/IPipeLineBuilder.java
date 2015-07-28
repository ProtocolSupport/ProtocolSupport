package com.github.shevchik.protocolsupport.protocol.core;

import io.netty.channel.Channel;
import com.github.shevchik.protocolsupport.api.ProtocolVersion;

public interface IPipeLineBuilder {

	public void buildPipeLine(Channel channel, ProtocolVersion version);

}
