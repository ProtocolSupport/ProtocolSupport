package protocolsupport.zmcpe.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;
import protocolsupport.zplatform.ServerPlatform;

public class PEBatchPacketEncoder extends MessageToByteEncoder<ByteBuf> {

	private final ProtocolSupportPacketDataSerializer serializer = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_PE);
	private final Compressor compressor = Compressor.create();

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		compressor.recycle();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf buf, ByteBuf out) throws Exception {
		if (buf.readableBytes() <= ServerPlatform.get().getMiscUtils().getCompressionThreshold()) {
			out.writeBytes(buf);
		} else {
			//pack packets (only 1 packet actually)
			serializer.clear();
			serializer.writeByteArray(buf);
			byte[] compressed = compressor.compress(ProtocolSupportPacketDataSerializer.toArray(serializer));
			//write batch id
			out.writeByte(PEPacketIDs.BATCH);
			//write payload
			ProtocolSupportPacketDataSerializer.writeVarInt(out, compressed.length);
			out.writeBytes(compressed);
		}
	}

}
