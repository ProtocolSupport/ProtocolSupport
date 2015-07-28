package protocolsupport.protocol.transformer.v_1_7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketPrepender;

public class PacketPrepender implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		int readableBytes = input.readableBytes();
		int length = PacketDataSerializer.a(readableBytes);
		if (length > 3) {
			throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
		}
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(output, ProtocolVersion.MINECRAFT_1_7_10);
		packetDataSerializer.ensureWritable(length + readableBytes);
		packetDataSerializer.writeVarInt(readableBytes);
		packetDataSerializer.writeBytes(input, input.readerIndex(), readableBytes);
	}

}
