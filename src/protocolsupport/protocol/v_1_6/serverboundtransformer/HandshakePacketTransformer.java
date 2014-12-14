package protocolsupport.protocol.v_1_6.serverboundtransformer;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.EnumProtocol;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketStatusInStart;
import protocolsupport.protocol.DataStorage.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class HandshakePacketTransformer implements PacketTransformer {

	@Override
	public Packet[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException {
		switch (packetId) {
			case 0xFE: {
				Packet[] packets = new Packet[2];
				Packet packet = EnumProtocol.HANDSHAKING.a(EnumProtocolDirection.SERVERBOUND, 0x00);
				PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_1_8);
				serializer.readUnsignedByte();
				serializer.readUnsignedByte();
				serializer.readString(32767);
				serializer.readUnsignedShort();
				serializer.readUnsignedByte();
				packetdata.writeVarInt(ProtocolVersion.MINECRAFT_1_8.getId());
				packetdata.writeString(serializer.readString(32767));
				packetdata.writeShort(serializer.readInt());
				packetdata.writeVarInt(1);
				packet.a(packetdata);
				packets[0] = packet;
				packets[1] = new PacketStatusInStart();
				return packets;
			}
		}
		return null;
	}

}
