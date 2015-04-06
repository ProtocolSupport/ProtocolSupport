package protocolsupport.protocol.v_1_7.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.pipeline.PublicPacketDecoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;

public class PacketDecoder extends ByteToMessageDecoder implements PublicPacketDecoder {

	private ProtocolVersion version;
	public PacketDecoder(ProtocolVersion version) {
		this.version = version;
	}

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@SuppressWarnings("unchecked")
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> list) throws Exception {
		if (bytebuf.readableBytes() == 0) {
			return;
		}
		Channel channel = ctx.channel();
		final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(bytebuf, version);
		final int packetId = packetDataSerializer.readVarInt();
		final Packet<PacketListener> packet = channel.attr(currentStateAttrKey).get().a(direction, packetId);
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

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ProtocolStorage.clearData(ctx.channel().remoteAddress());
		ProtocolStorage.clearData(Utils.getNetworkManagerSocketAddress(ctx.channel()));
	}

}
