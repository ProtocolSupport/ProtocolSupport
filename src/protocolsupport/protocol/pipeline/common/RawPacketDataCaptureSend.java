package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import protocolsupport.protocol.ConnectionImpl;

public class RawPacketDataCaptureSend extends ChannelOutboundHandlerAdapter {

	private final ConnectionImpl connection;
	public RawPacketDataCaptureSend(ConnectionImpl connection) {
		this.connection = connection;
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (msg instanceof ByteBuf) {
			ByteBuf data = (ByteBuf) msg;
			msg = connection.handleRawPacketSend(data);
			if (msg == null) {
				promise.setSuccess();
				return;
			}
		}
		super.write(ctx, msg, promise);
	}

}
