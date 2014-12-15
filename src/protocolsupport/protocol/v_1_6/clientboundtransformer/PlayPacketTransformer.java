package protocolsupport.protocol.v_1_6.clientboundtransformer;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.Packet;

public class PlayPacketTransformer implements PacketTransformer {

	//TODO: Create a simple array for transforming new packet ids to old

	@Override
	public void tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		packet.b(packetdata);
		switch (packetId) {
			case 0x00: { //PacketPlayOutKeepAlive
				serializer.writeByte(0x00);
				serializer.writeInt(packetdata.readVarInt());
				return;
			}
			case 0x01: { //PacketPlayOutLogin
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
				serializer.writeByte(0x03);
				serializer.writeString(ChatSerializer.a(packetdata.d()));
				return;
			}
			case 0x03: { //PacketPlayOutUpdateTime
				serializer.writeByte(0x04);
				serializer.writeLong(packetdata.readLong());
				serializer.writeLong(packetdata.readLong());
				return;
			}
			case 0x04: { //PacketPlayOutEntityEquipment
				serializer.writeByte(0x05);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readUnsignedShort());
				serializer.a(packetdata.i());
				return;
			}
			case 0x05: { //PacketPlayOutSpawnPosition
				serializer.writeByte(0x06);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				return;
			}
			case 0x06: { //PacketPlayOutUpdateHealth
				serializer.writeByte(0x08);
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeFloat(packetdata.readFloat());
				return;
			}
			case 0x07: { //PacketPlayOutRespawn
				serializer.writeByte(0x09);
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeShort(256);
				serializer.writeString(packetdata.readString(32767));
				return;
			}
			case 0x08: { // PacketPlayOutPosition
				serializer.writeByte(0x0D);
				Player player = DataStorage.getPlayer(channel.remoteAddress());
				double x = packetdata.readDouble();
				double y = packetdata.readDouble() /*+ 1.63*/;
				double z = packetdata.readDouble();
				float yaw = packetdata.readFloat();
				float pitch = packetdata.readFloat();
				short field = packetdata.readUnsignedByte();
				Location location = player.getLocation();
				if ((field & 0x01) != 0) {
					x += location.getX();
				}
				if ((field & 0x02) != 0) {
					y += location.getY();
				}
				if ((field & 0x04) != 0) {
					z += location.getX();
				}
				if ((field & 0x08) != 0) {
					yaw += location.getYaw();
				}
				if ((field & 0x10) != 0) {
					pitch += location.getPitch();
				}
				serializer.writeDouble(x);
				serializer.writeDouble(y + 1.63);
				serializer.writeDouble(y);
				serializer.writeDouble(z);
				serializer.writeFloat(yaw);
				serializer.writeFloat(pitch);
				serializer.writeBoolean(false);
				return;
			}
			case 0x09: { //PacketPlayOutHeldItemSlot
				serializer.writeByte(0x10);
				serializer.writeShort(packetdata.readUnsignedByte());
				return;
			}
			case 0x0A: { //PacketPlayOutBed
				serializer.writeByte(0x11);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(0);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				return;
			}
			case 0x0B: { //PacketPlayOutAnimation
				serializer.writeByte(0x12);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readUnsignedByte());
				return;
			}
		}
	}

}
