package protocolsupport.protocol.core.wrapped;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import protocolsupport.protocol.core.IPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;

public class WrappedDecoder extends ByteToMessageDecoder {

	private IPacketDecoder realDecoder = new IPacketDecoder() {
		@Override
		public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		}
	};

	public void setRealDecoder(IPacketDecoder realDecoder) {
		this.realDecoder = realDecoder;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		realDecoder.decode(ctx, input, list);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ProtocolStorage.clearData(ctx.channel().remoteAddress());
		ProtocolStorage.clearData(Utils.getNetworkManagerSocketAddress(ctx.channel()));
	}

}
