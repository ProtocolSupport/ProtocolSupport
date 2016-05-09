package protocolsupport.protocol.packet.v_legacy;

import io.netty.channel.Channel;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;

public class PipeLineBuilder implements IPipeLineBuilder {

	private static final LegacyLoginAndPingHandler legacyHandler = new LegacyLoginAndPingHandler();

	@Override
	public void buildPipeLine(Channel channel, ProtocolVersion version) {
		channel.pipeline().addAfter(ChannelHandlers.TIMEOUT, ChannelHandlers.LEGACY_KICK, legacyHandler);
	}

}
