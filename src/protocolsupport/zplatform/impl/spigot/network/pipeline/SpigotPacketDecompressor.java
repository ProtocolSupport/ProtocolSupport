package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class SpigotPacketDecompressor extends net.minecraft.server.v1_11_R1.PacketDecompressor {

	private final Inflater inflater = new Inflater();

	public SpigotPacketDecompressor(int threshold) {
		super(threshold);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		inflater.end();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf from, List<Object> list) throws DataFormatException {
		if (!from.isReadable()) {
			return;
		}
		int uncompressedlength = ProtocolSupportPacketDataSerializer.readVarInt(from);
		if (uncompressedlength == 0) {
			list.add(from.readBytes(from.readableBytes()));
		} else {
			this.inflater.setInput(ProtocolSupportPacketDataSerializer.toArray(from));
			byte[] uncompressed = new byte[uncompressedlength];
			this.inflater.inflate(uncompressed);
			list.add(Unpooled.wrappedBuffer(uncompressed));
			this.inflater.reset();
		}
	}

}
