package protocolsupport.protocol.transformer.v_1_8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketPrepender;

public class PacketPrepender implements IPacketPrepender {

	@Override
	public void prepend(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
		final int readableBytes = input.readableBytes();
		final int a = PacketDataSerializer.a(readableBytes);
		if (a > 3) {
			throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
		}
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(output, ProtocolVersion.MINECRAFT_1_8);
		packetDataSerializer.ensureWritable(a + readableBytes);
		packetDataSerializer.writeVarInt(readableBytes);
		packetDataSerializer.writeBytes(input, input.readerIndex(), readableBytes);
	}

}
