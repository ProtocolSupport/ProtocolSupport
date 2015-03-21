package protocolsupport.protocol.v_1_8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketDataSerializer;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.protocol.PublicPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;

public class PacketDecoder extends ByteToMessageDecoder implements PublicPacketDecoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@SuppressWarnings("unchecked")
	@Override
	protected void decode(final ChannelHandlerContext ctx, final ByteBuf bytebuf, final List<Object> list) throws IOException, IllegalAccessException, InstantiationException {
		if (bytebuf.readableBytes() == 0) {
			return;
		}
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(bytebuf);
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

	@Override
	public void publicDecode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		decode(ctx, input, list);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ProtocolStorage.clearData(ctx.channel().remoteAddress());
		ProtocolStorage.clearData(Utils.getNetworkManagerSocketAddress(ctx.channel()));
	}

}
