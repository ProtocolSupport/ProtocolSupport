package protocolsupport.protocol.v_1_7.clientboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.PublicPacketEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> implements PublicPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private final PacketTransformer[] transformers = new PacketTransformer[] {
			new HandshakePacketTransformer(),
			new PlayPacketTransformer(),
			new StatusPacketTransformer(),
			new LoginPacketTransformer()
	};

	private boolean[] blockedPlayPackets = new boolean[256];
	{
		//packet from 1.8
		for (int i = 0x41; i < 0x49; i++) {
			blockedPlayPackets[i] = true;
		}
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		final Integer packetId = currentProtocol.a(direction, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		if ((currentProtocol == EnumProtocol.PLAY) && blockedPlayPackets[packetId]) {
			return;
		}
		PacketDataSerializer serializer = new PacketDataSerializer(output, DataStorage.getVersion(channel.remoteAddress()));
		transformers[currentProtocol.ordinal()].tranform(ctx, packetId, packet, serializer);
	}

	@Override
	public void publicEncode(ChannelHandlerContext ctx, Packet packet, ByteBuf output) throws Exception {
		encode(ctx, packet, output);
	}

}
