package protocolsupport.protocol.transformer.v_1_8;

import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketDecoder;
import protocolsupport.utils.netty.WrappingBuffer;

public class PacketDecoder implements IPacketDecoder {

	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final WrappingBuffer buffer = new WrappingBuffer();
	private final PacketDataSerializer serializer = new PacketDataSerializer(buffer, ProtocolVersion.MINECRAFT_1_8);

	@SuppressWarnings("unchecked")
	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		buffer.setBuf(input);
		EnumProtocol currentProtocol = ctx.channel().attr(currentStateAttrKey).get();
		int packetId = serializer.readVarInt();
		Packet<PacketListener> packet = currentProtocol.a(EnumProtocolDirection.SERVERBOUND, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		packet.a(serializer);
		list.add(packet);
	}

}
