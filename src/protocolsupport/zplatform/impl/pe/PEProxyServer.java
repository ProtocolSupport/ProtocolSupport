package protocolsupport.zplatform.impl.pe;

import java.net.InetSocketAddress;

import org.bukkit.Bukkit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import raknetserver.RakNetServer;
import raknetserver.RakNetServer.UserChannelInitializer;

public class PEProxyServer {

	private final RakNetServer peserver = new RakNetServer(
		new InetSocketAddress(Bukkit.getIp().isEmpty() ? "0.0.0.0" : Bukkit.getIp(), Bukkit.getPort()),
		new PEProxyServerInfoHandler(),
		new UserChannelInitializer() {
			@Override
			public void init(Channel channel) {
				ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(new PECompressor());
				pipeline.addLast(new PEDecompressor());
				pipeline.addLast(PEDimSwitchLock.NAME, new PEDimSwitchLock());
				pipeline.addLast(PEProxyNetworkManager.NAME, new PEProxyNetworkManager());
			}
		}, 0xFE
	);

	public void start() {
		peserver.start();
	}

	public void stop() {
		peserver.stop();
	}

}
