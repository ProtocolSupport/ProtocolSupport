package com.github.shevchik.protocolsupport.protocol.core.initial;

import io.netty.channel.Channel;
import com.github.shevchik.protocolsupport.api.ProtocolVersion;

public class Minecraft152PingResponseTask implements Runnable {

	private InitialPacketDecoder initialDecoder;
	private Channel channel;

	public Minecraft152PingResponseTask(InitialPacketDecoder initialDecoder, Channel channel) {
		this.initialDecoder = initialDecoder;
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			initialDecoder.setProtocol(channel, initialDecoder.receivedData, ProtocolVersion.MINECRAFT_1_5_2);
		} catch (Throwable t) {
			if (channel.isOpen()) {
				channel.close();
			}
		}
	}

}
