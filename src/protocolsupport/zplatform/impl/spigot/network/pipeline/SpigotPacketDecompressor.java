package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.text.MessageFormat;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.Decompressor;
import protocolsupport.utils.netty.ReusableReadHeapBuffer;

public class SpigotPacketDecompressor extends net.minecraft.server.v1_14_R1.PacketDecompressor {

	protected static final int maxPacketLength = 2 << 20;

	private final Decompressor decompressor = Decompressor.create();

	public SpigotPacketDecompressor(int threshold) {
		super(threshold);
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
		int uncompressedlength = VarNumberSerializer.readVarInt(from);
		if (uncompressedlength == 0) {
			list.add(from.retain());
		} else {
			if (uncompressedlength > maxPacketLength) {
				throw new DecoderException(MessageFormat.format("Badly compressed packet - size of {0} is larger than protocol maximum of {1}", uncompressedlength, maxPacketLength));
			}
			ByteBuf out = ctx.alloc().heapBuffer(uncompressedlength);
			readBuffer.readFrom(from, (larray, loffset, llength) -> decompressor.decompressTo(out, larray, loffset, llength, uncompressedlength));
			list.add(out);
		}
	}

}
