package protocolsupport.utils.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public abstract class ChannelInitializer extends ChannelInboundHandlerAdapter {

	private static InternalLogger logger = InternalLoggerFactory.getInstance(io.netty.channel.ChannelInitializer.class);

	@Override
	public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
		try {
			initChannel(channelHandlerContext.channel());
		} catch (Throwable t) {
			exceptionCaught(channelHandlerContext, t);
		} finally {
			ChannelPipeline pipeline = channelHandlerContext.pipeline();
			if (pipeline.context(this) != null) {
				pipeline.remove(this);
			}
		}
		channelHandlerContext.pipeline().fireChannelRegistered();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable t) throws Exception {
		ChannelInitializer.logger.warn("Failed to initialize a channel. Closing: " + channelHandlerContext.channel(), t);
		channelHandlerContext.close();
	}

	protected abstract void initChannel(Channel channel) throws Exception;

}
