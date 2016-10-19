package protocolsupport.protocol.pipeline.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.WrappingBuffer;

public class PacketDecoder extends ByteToMessageDecoder {

	private final WrappingBuffer wrapper = new WrappingBuffer();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		EnumProtocol protocol = ctx.channel().attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		wrapper.setBuf(input);
		int packetId = ChannelUtils.readVarInt(wrapper);
		final Packet<?> packet = protocol.a(EnumProtocolDirection.SERVERBOUND, packetId);
		if (packet == null) {
			throw new DecoderException("Bad packet id " + packetId);
		}
		packet.a(nativeSerializer);
		if (nativeSerializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packet.getClass().getName() + ", bytes left: " + nativeSerializer.readableBytes());
		}
		list.add(packet);
	}

}
