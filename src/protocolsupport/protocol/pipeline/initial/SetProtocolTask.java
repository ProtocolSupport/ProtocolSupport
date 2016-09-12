package protocolsupport.protocol.pipeline.initial;

import io.netty.channel.Channel;
import protocolsupport.api.ProtocolVersion;

public class SetProtocolTask implements Runnable {

	private final InitialPacketDecoder initialDecoder;
	private final Channel channel;
	private final ProtocolVersion version;

	public SetProtocolTask(InitialPacketDecoder initialDecoder, Channel channel, ProtocolVersion version) {
		this.initialDecoder = initialDecoder;
		this.channel = channel;
		this.version = version;
	}

	@Override
	public void run() {
		try {
			initialDecoder.setProtocol(channel, version);
		} catch (Exception t) {
			channel.pipeline().firstContext().fireExceptionCaught(t);
		}
	}

}
