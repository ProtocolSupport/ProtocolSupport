package protocolsupport.protocol;

import java.io.IOException;
import java.util.List;

import protocolsupport.protocol.serverboundtransformer.HandshakePacketTransformer;
import protocolsupport.protocol.serverboundtransformer.LoginPacketTransformer;
import protocolsupport.protocol.serverboundtransformer.PacketTransformer;
import protocolsupport.protocol.serverboundtransformer.PlayPacketTransformer;
import protocolsupport.protocol.serverboundtransformer.StatusPacketTransformer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;

public class PacketDecoder extends ByteToMessageDecoder {

	private PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@Override
	protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf bytebuf, final List<Object> list) throws Exception {
		if (bytebuf.readableBytes() == 0) {
			return;
		}
		int version = DataStorage.getVersion(channelHandlerContext.channel().remoteAddress());
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(bytebuf, version);
		final int packetId = packetDataSerializer.e();
		final Packet packet = channelHandlerContext.channel().attr(currentStateAttrKey).get().a(direction, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		boolean needsRead = !transformers[channelHandlerContext.channel().attr(currentStateAttrKey).get().ordinal()].tranform(channelHandlerContext.channel(), packetId, packet, packetDataSerializer);
		if (needsRead) {
			packet.a(packetDataSerializer);
		}
		if (packetDataSerializer.readableBytes() > 0) {
			throw new IOException("Packet " + channelHandlerContext.channel().attr(currentStateAttrKey).get().a() + "/" + packetId + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetDataSerializer.readableBytes() + " bytes extra whilst reading packet " + packetId);
		}
		list.add(packet);
	}

}
