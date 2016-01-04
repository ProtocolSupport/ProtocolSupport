package protocolsupport.protocol.core.initial;

import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.MinecraftServer;
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
			initialDecoder.setProtocol(channel, initialDecoder.receivedData, version);
		} catch (Throwable t) {
			if (MinecraftServer.getServer().isDebugging()) {
				t.printStackTrace();
			}
			channel.close();
		}
	}

}
