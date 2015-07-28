package protocolsupport.protocol.transformer.v_1_7.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketDecoder;
import protocolsupport.utils.Allocator;

public class PacketDecoder implements IPacketDecoder {

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
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		Channel channel = ctx.channel();
		final PacketDataSerializer serializer = new PacketDataSerializer(input, version);
		final int packetId = serializer.readVarInt();
		final Packet<PacketListener> packet = channel.attr(currentStateAttrKey).get().a(direction, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), version);
		try {
			boolean needsRead = !transformers[channel.attr(currentStateAttrKey).get().ordinal()].tranform(channel, packetId, packet, serializer, packetdata);
			if (needsRead) {
				packet.a(serializer);
			}
		} finally {
			packetdata.release();
		}
		if (serializer.readableBytes() > 0) {
			throw new IOException("Packet " + channel.attr(currentStateAttrKey).get().a() + "/" + packetId + " (" + packet.getClass().getSimpleName() + ") was larger than expected, found " + serializer.readableBytes() + " bytes extra whilst reading packet " + packetId);
		}
		list.add(packet);
	}

}
