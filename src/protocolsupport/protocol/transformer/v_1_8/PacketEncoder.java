package protocolsupport.protocol.transformer.v_1_8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;

import java.io.IOException;

import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketDataSerializer;
import net.minecraft.server.v1_8_R2.PacketListener;
import protocolsupport.protocol.pipeline.PublicPacketEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet<PacketListener>> implements PublicPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		final Integer packetId = currentProtocol.a(direction, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		PacketDataSerializer serializer = new PacketDataSerializer(output);
		serializer.b(packetId);
		packet.b(serializer);
	}

	@Override
	public void publicEncode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		encode(ctx, packet, output);
	}

}
