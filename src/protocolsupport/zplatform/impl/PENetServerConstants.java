package protocolsupport.zplatform.impl;

import io.netty.channel.Channel;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import raknetserver.pipeline.raknet.RakNetPacketConnectionEstablishHandler.PingHandler;

import java.net.InetSocketAddress;

public class PENetServerConstants {

	public static final PingHandler PING_HANDLER = new PingHandler() {
		@Override
		public String getServerInfo(Channel channel) {
			ServerPingResponseEvent revent = AbstractStatusListener.createPingResponse(channel, (InetSocketAddress) channel.remoteAddress());
			return String.join(";",
				"MCPE",
				revent.getMotd().replace(";", ":"),
				"133", "1.2.0",
				String.valueOf(revent.getPlayers().size()), String.valueOf(revent.getMaxPlayers())
			);
		}
		@Override
		public void executeHandler(Runnable runnable) {
			AbstractStatusListener.executeTask(runnable);
		}
	};

	public static final int USER_PACKET_ID = 0xFE;

	public static final int TEST_PORT = 2222;

}
