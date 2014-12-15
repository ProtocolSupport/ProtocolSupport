package protocolsupport.protocol.v_1_6.clientboundtransformer;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.Packet;

public class PlayPacketTransformer implements PacketTransformer {

	//TODO: Create watched entities id map

	@Override
	public void tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		switch (packetId) {
			case 0x00: { //PacketPlayOutKeepAlive
				packet.b(packetdata);
				serializer.writeInt(0x00);
				serializer.writeInt(packetdata.readVarInt());
				break;
			}
			case 0x01: { //PacketPlayOutLogin
				packet.b(packetdata);
				serializer.writeInt(0x01);
				serializer.writeInt(packetdata.readInt());
				String levelType = packetdata.readString(32767);
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(0);
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeString(levelType);
				break;
			}
			case 0x02: { //PacketPlayOutChat
				packet.b(packetdata);
				serializer.writeInt(0x03);
				serializer.writeString(ChatSerializer.a(packetdata.d()));
			}
			case 0x05: { //PacketPlayOutSpawnPosition
				packet.b(packetdata);
				serializer.writeInt(0x06);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				break;
			}
		}
	}

}
