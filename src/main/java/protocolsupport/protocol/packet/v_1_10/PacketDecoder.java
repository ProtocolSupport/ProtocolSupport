package protocolsupport.protocol.packet.v_1_10;

import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.Packet;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.serializer.ChainedProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public class PacketDecoder implements IPacketDecoder {

	private final ChainedProtocolSupportPacketDataSerializer middlebuffer = new ChainedProtocolSupportPacketDataSerializer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		middlebuffer.setBuf(input);
		EnumProtocol currentProtocol = ctx.channel().attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		int packetId = ChannelUtils.readVarInt(input);
		Packet<?> packet = currentProtocol.a(EnumProtocolDirection.SERVERBOUND, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		packet.a(middlebuffer.getNativeSerializer());
		list.add(packet);
		if (middlebuffer.isReadable()) {
			throw new DecoderException("Did not read all data from packet " + packetId+ ", bytes left: " + middlebuffer.readableBytes());
		}
	}

}
