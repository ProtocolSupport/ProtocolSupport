package protocolsupport.protocol;

import protocolsupport.protocol.storage.ProtocolStorage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChannelInactiveListener extends ChannelInboundHandlerAdapter {

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		ProtocolStorage.clearData(ctx.channel().remoteAddress());
		ctx.fireChannelInactive();
	}

}
