package protocolsupport.protocol.v_1_8;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;

public class FullPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	@SuppressWarnings("unchecked")
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	public static void encodePacket(Channel channel, Packet packet, ByteBuf output) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer(), DataStorage.getVersion(channel.remoteAddress()));
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
        final Integer packetId = currentProtocol.a(direction, packet);
        if (packetId == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
		serializer.writeVarInt(packetId);
		packet.b(serializer);
		if (DataStorage.isCompressionEnabled(channel.remoteAddress())) {
			ByteBuf data = Compression.compress(serializer);
			writeVarInt(data.readableBytes(), output);
			output.writeBytes(data);
		} else {
			writeVarInt(serializer.readableBytes(), output);
			output.writeBytes(serializer);
		}
	}

	private static void writeVarInt(int value, ByteBuf buf) {
        while ((value & 0xFFFFFF80) != 0x0) {
        	buf.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        buf.writeByte(value);
	}

}
