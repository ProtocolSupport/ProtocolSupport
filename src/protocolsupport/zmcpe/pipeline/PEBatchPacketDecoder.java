package protocolsupport.zmcpe.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class PEBatchPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	private final ProtocolSupportPacketDataSerializer serializer = new ProtocolSupportPacketDataSerializer(null, ProtocolVersion.MINECRAFT_PE);
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
			serializer.setBuf(buf);
			//unpack the payload
			byte[] uncompresseddata = decompressor.decompress(serializer.readByteArray());
			serializer.setBuf(Unpooled.wrappedBuffer(uncompresseddata));
			//unpack all packets (every packet is prefixed with varint length)
			while (serializer.isReadable()) {
				list.add(Unpooled.wrappedBuffer(serializer.readByteArray()));
			}
		}
	}

}
