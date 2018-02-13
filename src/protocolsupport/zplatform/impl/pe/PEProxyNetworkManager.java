package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocolsupport.zplatform.ServerPlatform;

public class PEProxyNetworkManager extends SimpleChannelInboundHandler<ByteBuf> {

	private Channel serverconnection;

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		if (serverconnection != null) {
			serverconnection.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			System.err.println("PEProxy client connection exception occured");
			cause.printStackTrace();
		}
		ctx.channel().close();
		if (serverconnection != null) {
			serverconnection.close();
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf bytebuf) throws Exception {
		ByteBuf cbytebuf = Unpooled.copiedBuffer(bytebuf);
		if (serverconnection == null) {
			serverconnection = PEServerConnection.connectToServer(ctx.channel(), cbytebuf);
		} else {
			serverconnection.eventLoop().execute(() -> serverconnection.writeAndFlush(cbytebuf).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE));
		}
	}

}
