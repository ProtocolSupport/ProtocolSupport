package protocolsupport.protocol.v_1_7.serverboundtransformer;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R1.Packet;
import protocolsupport.protocol.storage.ProtocolStorage.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		serializer.readVarInt();
		packetdata.writeVarInt(ProtocolVersion.MINECRAFT_1_8.getId());
		packetdata.writeString(serializer.readString(32767));
		packetdata.writeShort(serializer.readUnsignedShort());
		packetdata.writeVarInt(serializer.readVarInt());
		packet.a(packetdata);
		return true;
	}

}
