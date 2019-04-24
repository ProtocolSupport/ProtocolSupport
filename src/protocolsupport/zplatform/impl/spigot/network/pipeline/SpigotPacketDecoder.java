package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import net.minecraft.server.v1_14_R1.EnumProtocol;
import net.minecraft.server.v1_14_R1.EnumProtocolDirection;
import net.minecraft.server.v1_14_R1.NetworkManager;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketDataSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.WrappingBuffer;

public class SpigotPacketDecoder extends ByteToMessageDecoder {

	private final WrappingBuffer wrapper = new WrappingBuffer();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws IllegalAccessException, InstantiationException {
		if (!input.isReadable()) {
			return;
		}
		EnumProtocol protocol = ctx.channel().attr(NetworkManager.c).get();
		wrapper.setBuf(input);
		int packetId = VarNumberSerializer.readVarInt(wrapper);
		Packet<?> packet = protocol.a(EnumProtocolDirection.SERVERBOUND, packetId);
		if (packet == null) {
			throw new DecoderException("Bad packet id " + packetId);
		}
		try {
			packet.a(nativeSerializer);
		} catch (IOException e) {
			throw new DecoderException(e);
		}
		if (nativeSerializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packet.getClass().getName() + ", bytes left: " + nativeSerializer.readableBytes());
		}
		list.add(packet);
	}

}
