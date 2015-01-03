package protocolsupport.protocol.v_1_7.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.PublicPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.ProtocolStorage.ProtocolVersion;

public class PacketDecoder extends ByteToMessageDecoder implements PublicPacketDecoder {

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> list) throws Exception {
		if (bytebuf.readableBytes() == 0) {
			return;
		}
		Channel channel = ctx.channel();
		ProtocolVersion version = ProtocolStorage.getVersion(channel.remoteAddress());
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(bytebuf, version);
		final int packetId = packetDataSerializer.readVarInt();
		final Packet packet = channel.attr(currentStateAttrKey).get().a(direction, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		boolean needsRead = !transformers[channel.attr(currentStateAttrKey).get().ordinal()].tranform(channel, packetId, packet, packetDataSerializer);
		if (needsRead) {
			packet.a(packetDataSerializer);
		}
		if (packetDataSerializer.readableBytes() > 0) {
			throw new IOException("Packet " + channel.attr(currentStateAttrKey).get().a() + "/" + packetId + " (" + packet.getClass().getSimpleName() + ") was larger than expected, found " + packetDataSerializer.readableBytes() + " bytes extra whilst reading packet " + packetId);
		}
		list.add(packet);
	}

	@Override
	public void publicDecode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		decode(ctx, input, list);
	}

}
