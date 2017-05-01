package protocolsupport.protocol.pipeline.version.v_pe;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.utils.netty.Decompressor;

public class PEBatchPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	private final Decompressor decompressor = Decompressor.create();

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		decompressor.recycle();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		buf.markReaderIndex();
		int packetId = buf.readUnsignedByte();
		if (packetId != PEPacketIDs.BATCH) {
			buf.resetReaderIndex();
			list.add(buf.retain());
		} else {
			//unpack the payload
			ByteBuf uncompresseddata = Unpooled.wrappedBuffer(decompressor.decompress(ByteArraySerializer.readByteArray(buf, ProtocolVersion.MINECRAFT_PE)));
			//unpack all packets
			while (uncompresseddata.isReadable()) {
				list.add(Unpooled.wrappedBuffer(ByteArraySerializer.readByteArray(uncompresseddata, ProtocolVersion.MINECRAFT_PE)));
			}
		}
	}

}
