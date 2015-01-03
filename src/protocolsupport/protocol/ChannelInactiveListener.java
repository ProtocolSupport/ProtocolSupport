package protocolsupport.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocolsupport.protocol.storage.ProtocolStorage;

public class ChannelInactiveListener extends ChannelInboundHandlerAdapter {

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		ProtocolStorage.clearData(ctx.channel().remoteAddress());
		ctx.fireChannelInactive();
	}

}
