package protocolsupport.zplatform.impl.spigot.injector.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SpigotNettyServerChannelHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
		Channel channel = (Channel) o;
		channel.pipeline().addLast(new SpigotServerConnectionChannel());
		super.channelRead(ctx, o);
	}

}
