package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.text.MessageFormat;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SpigotPacketDecompressor extends net.minecraft.server.v1_12_R1.PacketDecompressor {

	private static final int maxPacketLength = (int) Math.pow(2, 7 * 3);

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
		int uncompressedlength = VarNumberSerializer.readVarInt(from);
		if (uncompressedlength == 0) {
			list.add(from.readBytes(from.readableBytes()));
		} else {
			if (uncompressedlength > maxPacketLength) {
				throw new DecoderException(MessageFormat.format("Badly compressed packet - size of {0} is larger than protocol maximum of {1}", uncompressedlength, maxPacketLength));
			}
			inflater.setInput(MiscSerializer.readAllBytes(from));
			byte[] uncompressed = new byte[uncompressedlength];
			inflater.inflate(uncompressed);
			list.add(Unpooled.wrappedBuffer(uncompressed));
			inflater.reset();
		}
	}

}
