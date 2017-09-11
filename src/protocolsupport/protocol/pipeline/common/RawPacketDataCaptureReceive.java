package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocolsupport.protocol.ConnectionImpl;

public class RawPacketDataCaptureReceive extends ChannelInboundHandlerAdapter {

	private final ConnectionImpl connection;
	public RawPacketDataCaptureReceive(ConnectionImpl connection) {
		this.connection = connection;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf) {
			ByteBuf data = (ByteBuf) msg;
			msg = connection.handleRawPacketReceive(data);
			if (msg == null) {
				return;
			}
		}
		super.channelRead(ctx, msg);
	}

}
