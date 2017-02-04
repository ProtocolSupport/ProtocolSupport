package protocolsupport.zplatform.impl.glowstone.network.pipeline;

import org.bukkit.scheduler.BukkitRunnable;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
import protocolsupport.zplatform.impl.glowstone.network.handler.GlowStoneTickableListener;

public class GlowStoneSyncConnectionTicker extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		Channel channel = ctx.channel();
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!channel.isOpen()) {
					cancel();
					return;
				}
				Object listener = GlowStoneNetworkManagerWrapper.getFromChannel(channel).getPacketListener();
				if (listener instanceof GlowStoneTickableListener) {
					((GlowStoneTickableListener) listener).tick();
				}
			}
		}.runTaskTimer(null, 0, 1);
	}

}
