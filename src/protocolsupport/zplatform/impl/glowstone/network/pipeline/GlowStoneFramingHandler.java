//package protocolsupport.zplatform.impl.glowstone.network.pipeline;
//
//import java.util.List;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageCodec;
//import protocolsupport.protocol.pipeline.IPacketPrepender;
//import protocolsupport.protocol.pipeline.IPacketSplitter;
//import protocolsupport.protocol.pipeline.common.FakeFrameDecoder;
//import protocolsupport.protocol.pipeline.common.FakeFrameEncoder;
//
//public class GlowStoneFramingHandler extends ByteToMessageCodec<ByteBuf> {
//
//	private IPacketPrepender realPrepender = new FakeFrameEncoder();
//	private IPacketSplitter realSplitter = new FakeFrameDecoder();
//
//	public void setRealFraming(IPacketPrepender realPrepender, IPacketSplitter realSplitter) {
//		this.realPrepender = realPrepender;
//		this.realSplitter = realSplitter;
//	}
//
//	@Override
//	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
//		if (!input.isReadable()) {
//			return;
//		}
//		realSplitter.split(ctx, input, list);
//	}
//
//	@Override
//	protected void encode(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
//		if (!input.isReadable()) {
//			return;
//		}
//		realPrepender.prepend(ctx, input, output);
//	}
//
//}
