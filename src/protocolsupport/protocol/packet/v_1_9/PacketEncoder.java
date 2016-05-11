package protocolsupport.protocol.packet.v_1_9;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_9_R2.EnumProtocol;
import net.minecraft.server.v1_9_R2.EnumProtocolDirection;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.IPacketEncoder;
import protocolsupport.protocol.serializer.WrappingBufferPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public class PacketEncoder implements IPacketEncoder {

	private final WrappingBufferPacketDataSerializer serializer = WrappingBufferPacketDataSerializer.create(ProtocolVersion.getLatest());
	public PacketEncoder(ProtocolVersion version) {
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		final Integer packetId = currentProtocol.a(EnumProtocolDirection.CLIENTBOUND, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		serializer.setBuf(output);
		serializer.writeVarInt(packetId);
		packet.b(serializer);
	}

}
