package protocolsupport.zplatform.impl.pe;

import org.bukkit.Bukkit;

import io.netty.channel.Channel;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import raknetserver.pipeline.raknet.RakNetPacketConnectionEstablishHandler.PingHandler;

public class PENetServerConstants {

	public static final PingHandler PING_HANDLER = new PingHandler() {
		@Override
		public String getServerInfo(Channel channel) {
			//TODO: fake pspe packets for ping passthrough
			return String.join(";",
				"MCPE",
				Bukkit.getMotd().replace(";", ":"),
				"134", "1.2.0",
				String.valueOf(Bukkit.getOnlinePlayers().size()), String.valueOf(Bukkit.getMaxPlayers())
			);
		}
		@Override
		public void executeHandler(Runnable runnable) {
			AbstractStatusListener.executeTask(runnable);
		}
	};

	public static final int USER_PACKET_ID = 0xFE;

	public static final int TEST_PORT = 19138;

}
