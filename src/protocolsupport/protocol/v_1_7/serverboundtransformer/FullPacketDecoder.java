package protocolsupport.protocol.v_1_7.serverboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.DataStorage.ProtocolVersion;

public class FullPacketDecoder {

	private static final PacketTransformer[] transformers = new PacketTransformer[] {
		new HandshakePacketTransformer(),
		new PlayPacketTransformer(),
		new StatusPacketTransformer(),
		new LoginPacketTransformer()
	};

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	public static Packet decodePacket(Channel channel, final ByteBuf bytebuf) throws Exception {
		ProtocolVersion version = DataStorage.getVersion(channel.remoteAddress());
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
		return packet;
	}

}
