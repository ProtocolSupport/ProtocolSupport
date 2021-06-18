package protocolsupport.zplatform.impl.spigot.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import net.minecraft.network.PacketCompressor;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.utils.netty.RecyclableWrapCompressor;
import protocolsupport.utils.netty.ReusableReadHeapBuffer;

public class SpigotPacketCompressor extends PacketCompressor {

	protected final RecyclableWrapCompressor compressor = RecyclableWrapCompressor.create();
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
	protected void encode(ChannelHandlerContext ctx, ByteBuf from, ByteBuf to) {
		int readable = from.readableBytes();
		if (readable == 0) {
			return;
		}
		if (readable < this.threshold) {
			VarNumberCodec.writeVarInt(to, 0);
			to.writeBytes(from);
		} else {
			VarNumberCodec.writeVarInt(to, readable);
			try {
				readBuffer.readFrom(from, (larray, loffset, llength) -> compressor.compressTo(to, larray, loffset, llength));
			} catch (Exception e) {
				throw new EncoderException(e);
			}
		}
	}

	@Override
	protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf buf, boolean preferDirect) throws Exception {
		return ctx.alloc().heapBuffer(buf.readableBytes() + VarNumberCodec.MAX_LENGTH);
	}

}
