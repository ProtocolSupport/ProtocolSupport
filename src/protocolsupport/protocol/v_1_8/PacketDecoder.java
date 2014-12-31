package protocolsupport.protocol.v_1_8;

import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;

public class PacketDecoder extends ByteToMessageDecoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@Override
	protected void decode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> list) throws IOException {
		if (bytebuf.readableBytes() == 0) {
			return;
		}
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(bytebuf);
		final int packetId = packetDataSerializer.e();
		final Packet packet = ctx.channel().attr(currentStateAttrKey).get().a(direction, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		packet.a(packetDataSerializer);
		if (packetDataSerializer.readableBytes() > 0) {
			throw new IOException("Packet " + ctx.channel().attr(currentStateAttrKey).get().a() + "/" + packetId + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetDataSerializer.readableBytes() + " bytes extra whilst reading packet " + packetId);
		}
		list.add(packet);
	}

}
