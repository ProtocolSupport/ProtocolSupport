package protocolsupport.protocol.packet.v_1_9;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_9_R2.EnumProtocol;
import net.minecraft.server.v1_9_R2.EnumProtocolDirection;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.IPacketEncoder;
import protocolsupport.protocol.serializer.WrappingBufferPacketDataSerializer;

public class PacketEncoder implements IPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final WrappingBufferPacketDataSerializer serializer = WrappingBufferPacketDataSerializer.create(ProtocolVersion.getLatest());
	public PacketEncoder(ProtocolVersion version) {
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		final Integer packetId = currentProtocol.a(direction, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		serializer.setBuf(output);
		serializer.writeVarInt(packetId);
		packet.b(serializer);
	}

}
