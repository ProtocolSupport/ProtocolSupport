//package protocolsupport.zplatform.impl.glowstone.network.pipeline;
//
//import org.bukkit.scheduler.BukkitRunnable;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandler.Sharable;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import net.glowstone.net.GlowSession;
//import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
//import protocolsupport.zplatform.impl.glowstone.network.handler.GlowStoneTickableListener;
//
//@Sharable
//public class GlowStoneSyncTickerStarter extends ChannelInboundHandlerAdapter {
//
//	public static final GlowStoneSyncTickerStarter INSTANCE = new GlowStoneSyncTickerStarter();
//
//	protected GlowStoneSyncTickerStarter() {
//	}
//
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);
//		new SyncTicker(ctx.channel()).runTaskTimer(null, 0, 1);
//	}
//
//	private static class SyncTicker extends BukkitRunnable {
//
//		private final Channel channel;
//		private final GlowSession session;
//		public SyncTicker(Channel channel) {
//			this.channel = channel;
//			this.session = GlowStoneMiscUtils.getNetworkManager(channel.pipeline()).getSession();
//		}
//
//		@Override
//		public void run() {
//			if (!channel.isOpen()) {
//				cancel();
//				return;
//			}
//			Object listener = GlowStoneNetworkManagerWrapper.getPacketListener(session);
//			if (listener instanceof GlowStoneTickableListener) {
//				((GlowStoneTickableListener) listener).tick();
//			}
//		}
//
//	}
//
//}
