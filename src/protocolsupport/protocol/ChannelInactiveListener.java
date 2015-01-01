package protocolsupport.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChannelInactiveListener extends ChannelInboundHandlerAdapter {

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		DataStorage.clearData(ctx.channel().remoteAddress());
		ctx.fireChannelInactive();
	}

}
