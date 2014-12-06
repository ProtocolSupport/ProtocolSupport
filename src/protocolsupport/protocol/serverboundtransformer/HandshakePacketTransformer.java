package protocolsupport.protocol.serverboundtransformer;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import protocolsupport.protocol.ServerConnectionChannel;
import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.PacketDataSerializer;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		int version = serializer.readVarInt();
		if (version == 4 || version == 5) {
			ServerConnectionChannel.setVersion(channel.remoteAddress(), version);
			version = ServerConnectionChannel.CLIENT_1_8_PROTOCOL_VERSION;
		}
		packetdata.writeVarInt(version);
		packetdata.writeString(serializer.readString(32767));
		packetdata.writeShort(serializer.readUnsignedShort());
		packetdata.writeVarInt(serializer.readVarInt());
		packet.a(packetdata);
		return true;
	}

}
