package protocolsupport.protocol.v_1_6.serverboundtransformer;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;

import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.PacketListener;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class HandshakePacketTransformer implements PacketTransformer {

	@SuppressWarnings("unchecked")
	@Override
	public Packet<PacketListener>[] tranform(Channel channel, int packetId, PacketDataSerializer serializer) throws IOException, IllegalAccessException, InstantiationException {
		switch (packetId) {
			case 0xFE: { //Ping
				Packet<PacketListener>[] packets = new Packet[2];
				Packet<PacketListener> handshakepacket = EnumProtocol.HANDSHAKING.a(EnumProtocolDirection.SERVERBOUND, 0x00);
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
				handshakepacket.a(packetdata);
				packets[0] = handshakepacket;
				packets[1] = EnumProtocol.STATUS.a(EnumProtocolDirection.SERVERBOUND, 0x00);
				return packets;
			}
			case 0x02: { //Handsahke
				Packet<PacketListener>[] packets = new Packet[2];
				Packet<PacketListener> handshakepacket = EnumProtocol.HANDSHAKING.a(EnumProtocolDirection.SERVERBOUND, 0x00);
				PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_1_8);
				serializer.readUnsignedByte();
				packetdata.writeVarInt(ProtocolVersion.MINECRAFT_1_8.getId());
				String username = serializer.readString(16);
				packetdata.writeString(serializer.readString(32767));
				packetdata.writeShort(serializer.readInt());
				packetdata.writeVarInt(2);
				handshakepacket.a(packetdata);
				packets[0] = handshakepacket;
				packetdata.clear();
				Packet<PacketListener> loginstartpacket = EnumProtocol.LOGIN.a(EnumProtocolDirection.SERVERBOUND, 0x00);
				packetdata.writeString(username);
				loginstartpacket.a(packetdata);
				packets[1] = loginstartpacket;
				return packets;
			}
		}
		return null;
	}

}
