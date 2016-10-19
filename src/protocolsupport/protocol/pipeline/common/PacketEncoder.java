package protocolsupport.protocol.pipeline.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
import net.minecraft.server.v1_10_R1.PacketListener;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.WrappingBuffer;

public class PacketEncoder extends MessageToByteEncoder<Packet<PacketListener>> {

	private final WrappingBuffer wrapper = new WrappingBuffer();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf data) throws Exception {
		EnumProtocol currentProtocol = ctx.channel().attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		final Integer packetId = currentProtocol.a(EnumProtocolDirection.CLIENTBOUND, packet);
		if (packetId == null) {
			throw new EncoderException("Can't serialize unregistered packet " + packet.getClass().getName());
		}
		wrapper.setBuf(data);
		ChannelUtils.writeVarInt(wrapper, packetId);
		packet.b(nativeSerializer);
	}

}
