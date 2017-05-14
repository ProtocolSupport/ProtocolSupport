package protocolsupport.zplatform.impl.spigot.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.Compressor;

public class SpigotPacketCompressor extends net.minecraft.server.v1_12_R1.PacketCompressor {

	private final Compressor compressor = Compressor.create();
	private final int threshold;

	public SpigotPacketCompressor(int threshold) {
		super(threshold);
		this.threshold = threshold;
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		compressor.recycle();
	}

	@Override
	protected void a(ChannelHandlerContext ctx, ByteBuf from, ByteBuf to)  {
		int readable = from.readableBytes();
		if (readable == 0) {
			return;
		}
		if (readable < this.threshold) {
			VarNumberSerializer.writeVarInt(to, 0);
			to.writeBytes(from);
		} else {
			VarNumberSerializer.writeVarInt(to, readable);
			to.writeBytes(compressor.compress(MiscSerializer.readAllBytes(from)));
		}
	}

}
