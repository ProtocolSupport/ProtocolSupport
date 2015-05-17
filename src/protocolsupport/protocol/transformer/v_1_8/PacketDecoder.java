package protocolsupport.protocol.transformer.v_1_8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.protocol.pipeline.IPacketDecoder;

public class PacketDecoder implements IPacketDecoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@SuppressWarnings("unchecked")
	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(input);
		final int packetId = packetDataSerializer.e();
		final Packet<PacketListener> packet = ctx.channel().attr(currentStateAttrKey).get().a(direction, packetId);
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
