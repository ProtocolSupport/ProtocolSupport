package protocolsupport.zplatform.impl.spigot.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.utils.netty.ReusableReadHeapBuffer;

public class SpigotPacketCompressor extends net.minecraft.server.v1_14_R1.PacketCompressor {

	protected final Compressor compressor = Compressor.create();
	protected final int threshold;

	public SpigotPacketCompressor(int threshold) {
		super(threshold);
		this.threshold = threshold;
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		compressor.recycle();
	}

	protected final ReusableReadHeapBuffer readBuffer = new ReusableReadHeapBuffer();

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf from, ByteBuf to) throws Exception {
		int readable = from.readableBytes();
		if (readable == 0) {
			return;
		}
		if (readable < this.threshold) {
			VarNumberSerializer.writeVarInt(to, 0);
			to.writeBytes(from);
		} else {
			VarNumberSerializer.writeVarInt(to, readable);
			readBuffer.readFrom(from, (larray, loffset, llength) -> compressor.compressTo(to, larray, loffset, llength));
		}
	}

	@Override
	protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf buf, boolean preferDirect) throws Exception {
		return ctx.alloc().heapBuffer(buf.readableBytes() + VarNumberSerializer.MAX_LENGTH);
	}

}
