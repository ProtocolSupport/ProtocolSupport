package protocolsupport.zplatform.impl.spigot.network.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.utils.netty.WrappingByteBuf;

public class SpigotPacketDecoder extends ByteToMessageDecoder {

	private final WrappingByteBuf wrapper = new WrappingByteBuf();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws IllegalAccessException, InstantiationException {
		if (!input.isReadable()) {
			return;
		}
		EnumProtocol protocol = ctx.channel().attr(NetworkManager.c).get();
		wrapper.setBuf(input);
		int packetId = VarNumberCodec.readVarInt(wrapper);
		Packet<?> packet = protocol.a(EnumProtocolDirection.a, packetId, nativeSerializer);
		if (packet == null) {
			throw new DecoderException("Bad packet id " + packetId);
		}
		if (nativeSerializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packet.getClass().getName() + ", bytes left: " + nativeSerializer.readableBytes());
		}
		list.add(packet);
	}

}
