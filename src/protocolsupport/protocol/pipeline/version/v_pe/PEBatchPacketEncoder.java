package protocolsupport.protocol.pipeline.version.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zplatform.ServerPlatform;

public class PEBatchPacketEncoder extends MessageToByteEncoder<ByteBuf> {

	private final ByteBuf packbuffer = Allocator.allocateBuffer();
	private final Compressor compressor = Compressor.create();

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		packbuffer.release();
		compressor.recycle();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf buf, ByteBuf out) throws Exception {
		if (buf.readableBytes() <= ServerPlatform.get().getMiscUtils().getCompressionThreshold()) {
			out.writeBytes(buf);
		} else {
			//pack packets (only 1 packet actually)
			packbuffer.clear();
			ByteArraySerializer.writeByteArray(packbuffer, ProtocolVersion.MINECRAFT_PE, buf);
			byte[] compressed = compressor.compress(MiscSerializer.readAllBytes(packbuffer));
			//write batch id
			out.writeByte(PEPacketIDs.BATCH);
			//write payload
			ByteArraySerializer.writeByteArray(out, ProtocolVersion.MINECRAFT_PE, compressed);
		}
	}

}
