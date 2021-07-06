package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.text.MessageFormat;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import net.minecraft.network.PacketDecompressor;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.utils.netty.Decompressor;
import protocolsupport.utils.netty.ReusableReadHeapBuffer;

public class SpigotPacketDecompressor extends PacketDecompressor {

	protected static final int length_max = 1 << 23;

	protected final int threshold;
	protected final Decompressor decompressor = Decompressor.create();

	public SpigotPacketDecompressor(int threshold) {
		super(threshold, true);
		this.threshold = threshold;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		decompressor.recycle();
	}

	protected final ReusableReadHeapBuffer readBuffer = new ReusableReadHeapBuffer();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf from, List<Object> list) throws Exception {
		if (!from.isReadable()) {
			return;
		}
		int length = VarNumberCodec.readVarInt(from);
		if (length == 0) {
			list.add(from.retain());
		} else {
			if (length < threshold) {
				throw new DecoderException(MessageFormat.format("Badly compressed packet - size of {0} is lower than compression threshold of {1}", length, threshold));
			}
			if (length > length_max) {
				throw new DecoderException(MessageFormat.format("Badly compressed packet - size of {0} is larger than protocol maximum of {1}", length, length_max));
			}
			ByteBuf out = ctx.alloc().heapBuffer(length);
			readBuffer.readFrom(from, (larray, loffset, llength) -> decompressor.decompressTo(out, larray, loffset, llength, length));
			list.add(out);
		}
	}

}
