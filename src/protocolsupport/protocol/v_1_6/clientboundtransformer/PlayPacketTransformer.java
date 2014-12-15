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
				serializer.writeByte(0x00);
				serializer.writeInt(packetdata.readVarInt());
				return;
			}
			case 0x01: { //PacketPlayOutLogin
				packet.b(packetdata);
				serializer.writeByte(0x01);
				serializer.writeInt(packetdata.readInt());
				int gamemode = packetdata.readUnsignedByte();
				int dimension = packetdata.readUnsignedByte();
				int difficulty = packetdata.readUnsignedByte();
				int maxplayers = packetdata.readUnsignedByte();
				serializer.writeString(packetdata.readString(32767));
				serializer.writeByte(gamemode);
				serializer.writeByte(dimension);
				serializer.writeByte(difficulty);
				serializer.writeByte(0);
				serializer.writeByte(maxplayers);
				return;
			}
			case 0x02: { //PacketPlayOutChat
				packet.b(packetdata);
				serializer.writeByte(0x03);
				serializer.writeString(ChatSerializer.a(packetdata.d()));
				return;
			}
			case 0x03: { //PacketPlayOutUpdateTime
				packet.b(packetdata);
				serializer.writeByte(0x04);
				serializer.writeLong(packetdata.readLong());
				serializer.writeLong(packetdata.readLong());
				return;
			}
			case 0x04: { //PacketPlayOutEntityEquipment
				packet.b(packetdata);
				serializer.writeByte(0x05);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readUnsignedShort());
				serializer.a(packetdata.i());
				return;
			}
			case 0x05: { //PacketPlayOutSpawnPosition
				packet.b(packetdata);
				serializer.writeByte(0x06);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				return;
			}
		}
	}

}
