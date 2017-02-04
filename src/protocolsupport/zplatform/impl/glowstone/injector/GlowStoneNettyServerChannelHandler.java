package protocolsupport.zplatform.impl.glowstone.injector;

import java.util.Map.Entry;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class GlowStoneNettyServerChannelHandler extends ChannelInboundHandlerAdapter {

	private static final ServerConnectionChannelPlacer serverConnectionChannelPlacer = new ServerConnectionChannelPlacer();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
		Channel channel = (Channel) o;
		channel.pipeline().addFirst(serverConnectionChannelPlacer);
		super.channelRead(ctx, o);
	}

	private static class ServerConnectionChannelPlacer extends ChannelInitializer<Channel> {

		private static final GlowStoneServerConnectionChannel serverConnectionChannel = new GlowStoneServerConnectionChannel();

		@Override
		protected void initChannel(Channel channel) throws Exception {
			String nativeServerConnection = findNativeServerConnectionChannelHandlerName(channel.pipeline());
			if (nativeServerConnection != null) {
				channel.pipeline().addAfter(nativeServerConnection, "ps_server_connection", serverConnectionChannel);
			} else {
				channel.pipeline().addLast(serverConnectionChannel);
			}
		}

		private static String findNativeServerConnectionChannelHandlerName(ChannelPipeline pipeline) {
			for (Entry<String, ChannelHandler> entry : pipeline) {
				if (entry.getValue().getClass().getName().equals("net.glowstone.net.pipeline.GlowChannelInitializer")) {
					return entry.getKey();
				}
			}
			return null;
		}
	}

}
