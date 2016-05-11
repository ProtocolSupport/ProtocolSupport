package protocolsupport.protocol.packet.v_1_9;

import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_9_R2.EnumProtocol;
import net.minecraft.server.v1_9_R2.EnumProtocolDirection;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.serializer.WrappingBufferPacketDataSerializer;

public class PacketDecoder implements IPacketDecoder {

	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final WrappingBufferPacketDataSerializer serializer = WrappingBufferPacketDataSerializer.create(ProtocolVersion.getLatest());

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		serializer.setBuf(input);
		EnumProtocol currentProtocol = ctx.channel().attr(currentStateAttrKey).get();
		int packetId = serializer.readVarInt();
		Packet<?> packet = currentProtocol.a(EnumProtocolDirection.SERVERBOUND, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		packet.a(serializer);
		list.add(packet);
		if (serializer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packetId+ ", bytes left: " + serializer.readableBytes());
		}
	}

}
