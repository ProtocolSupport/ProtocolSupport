package protocolsupport.zplatform.impl.glowstone.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GlowStoneNettyServerChannelHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
		Channel channel = (Channel) o;
		channel.pipeline().addFirst(new GlowStoneServerConnectionChannel());
		super.channelRead(ctx, o);
	}

}
