package protocolsupport.protocol.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.DataStorage.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;

public class FullPacketDecoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.SERVERBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	public static Packet decodePacket(Channel channel, ByteBuf bytebuf) throws Exception {
		if (DataStorage.isCompressionEnabled(channel.remoteAddress())) {
			bytebuf = Compression.decompress(bytebuf);
		}
		ProtocolVersion version = DataStorage.getVersion(channel.remoteAddress());
		final PacketDataSerializer serializer = new PacketDataSerializer(bytebuf, version);
		final int packetId = serializer.readVarInt();
		final Packet packet = channel.attr(currentStateAttrKey).get().a(direction, packetId);
		if (packet == null) {
			throw new IOException("Bad packet id " + packetId);
		}
		packet.a(serializer);
		if (serializer.readableBytes() > 0) {
			throw new IOException("Packet " + channel.attr(currentStateAttrKey).get().a() + "/" + packetId + " (" + packet.getClass().getSimpleName() + ") was larger than expected, found " + serializer.readableBytes() + " bytes extra whilst reading packet " + packetId);
		}
		return packet;
	}

}
