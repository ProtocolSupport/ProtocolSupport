package protocolsupport.protocol.clientboundtransformer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.zip.Deflater;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.ServerConnectionChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.Packet;

public class PlayPacketTransformer implements PacketTransformer {

	@Override
	public boolean tranform(Channel channel, int packetId, Packet packet, PacketDataSerializer serializer) throws IOException {
		if (serializer.getVersion() == ServerConnectionChannel.CLIENT_1_8_PROTOCOL_VERSION) {
			return false;
		}
		PacketDataSerializer packetdata = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
		switch (packetId) {
			case 0x0A: { // PacketPlayOutBed
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				return true;
			}
			case 0x24: { // PacketPlayOutBlockAction
				packet.b(packetdata);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeVarInt(packetdata.readVarInt());
				return true;
			}
			case 0x25: { // PacketPlayOutBlockBreakAnimation
				packet.b(packetdata);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				serializer.writeByte(packetdata.readUnsignedByte());
				return true;
			}
			case 0x23: { // PacketPlayOutBlockChange
				packet.b(packetdata);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				int stateId = packetdata.readVarInt();
				serializer.writeVarInt(stateId >> 4);
				serializer.writeByte(stateId & 0xF);
				return true;
			}
			case 0x02: { // PacketPlayOutChat
				packet.b(packetdata);
				serializer.a(ChatSerializer.a(packetdata.d()));
				return true;
			}
			case 0x0D: { // PacketPlayOutCollect
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readVarInt());
				return true;
			}
			case 0x3F: { // PacketPlayOutCustomPayload
				packet.b(packetdata);
				serializer.writeString(packetdata.readString(20));
				ByteBuf data = packetdata.readBytes(packetdata.readableBytes());
				serializer.writeShort(data.readableBytes());
				serializer.writeBytes(data);
				return true;
			}
			case 0x14: { // PacketPlayOutEntity
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				return true;
			}
			case 0x13: { // PacketPlayOutEntityDestroy
				packet.b(packetdata);
				int count = packetdata.readVarInt();
				serializer.writeByte(count);
				for (int i = 0; i < count; i++) {
					serializer.writeInt(packetdata.readVarInt());
				}
				return true;
			}
			case 0x1D: { // PacketPlayOutEntityEffect
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readVarInt());
				return true;
			}
			case 0x04: { // PacketPlayOutEntityEquipment
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readShort());
				serializer.a(packetdata.i());
				return true;
			}
			case 0x19: { // PacketPlayOutEntityHeadRotation
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				return true;
			}
			case 0x16: { // PacketPlayOutEntityLook
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return true;
			}
			case 0x1C: { // PacketPlayOutEntityMetadata
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				// TODO: filter datawatcher data
				serializer.writeBytes(packetdata.readBytes(packetdata.readableBytes()));
				return true;
			}
			case 0x18: { // PacketPlayOutEntityTeleport
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readInt());
				// TODO: height correction
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return true;
			}
			case 0x12: { // PacketPlayOutEntityVelocity
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				return true;
			}
			case 0x1F: { // PacketPlayOutExperience
				packet.b(packetdata);
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeShort(packetdata.readVarInt());
				return true;
			}
			case 0x00: { // PacketPlayOutKeepAlive
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				return true;
			}
			case 0x01: { // PacketPlayOutLogin
				packet.b(packetdata);
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeString(packetdata.readString(16));
				return true;
			}
			case 0x34: { // PacketPlayOutMap
				// TODO
				break;
			}
			case 0x21: { // PacketPlayOutMapChunk
				packet.b(packetdata);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeBoolean(packetdata.readBoolean());
				int bitmap = packetdata.readShort() & 0xFFFF;
				serializer.writeShort(bitmap);
				serializer.writeShort(0);
				byte[] data = ChunkUtils.to17ChunkData(packetdata.a(), bitmap);
				final Deflater deflater = new Deflater(4);
				try {
					deflater.setInput(data, 0, data.length);
					deflater.finish();
					byte[] networkdata = new byte[data.length];
					int size = deflater.deflate(networkdata);
					serializer.writeInt(size);
					serializer.writeBytes(networkdata, 0, size);
				} finally {
					deflater.end();
				}
				return true;
			}
			case 0x26: { // PacketPlayOutMapChunkBulk
				packet.b(packetdata);
				// read data
				boolean skylight = packetdata.readBoolean();
				int count = packetdata.readVarInt();
				int[] x = new int[count];
				int[] y = new int[count];
				int[] bitmap = new int[count];
				for (int i = 0; i < count; i++) {
					x[i] = packetdata.readInt();
					y[i] = packetdata.readInt();
					bitmap[i] = packetdata.readShort() & 0xFFFF;
				}
				byte[][] data = new byte[count][];
				byte[] ldata;
				int pos = 0;
				for (int i = 0; i < count; i++) {
					data[i] = ChunkUtils.to17ChunkData(packetdata.readBytes(ChunkUtils.calcDataSize(Integer.bitCount(bitmap[i]), skylight, true)).array(), bitmap[i]);
					pos += data[i].length;
				}
				ldata = new byte[pos];
				pos = 0;
				for (int i = 0; i < data.length; i++) {
					System.arraycopy(data[i], 0, ldata, pos, data[i].length);
					pos += data[i].length;
				}
				// compress
				final Deflater deflater = new Deflater(4);
				try {
					deflater.setInput(ldata, 0, ldata.length);
					deflater.finish();
					byte[] networkdata = new byte[ldata.length + 100];
					int size = deflater.deflate(networkdata);
					// write data
					serializer.writeShort(count);
					serializer.writeInt(size);
					serializer.writeBoolean(skylight);
					serializer.writeBytes(networkdata, 0, size);
					for (int i = 0; i < count; i++) {
						serializer.writeInt(x[i]);
						serializer.writeInt(y[i]);
						serializer.writeShort(bitmap[i] & 0xFFFF);
						serializer.writeShort(0);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				} finally {
					deflater.end();
				}
				return true;
			}
			case 0x22: { // PacketPlayOutMultiBlockChange
				packet.b(packetdata);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				int count = packetdata.readVarInt();
				serializer.writeShort(count);
				final ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(count * 4);
				final DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
				for (int i = 0; i < count; i++) {
					int coord = packetdata.readUnsignedShort();
					int id = packetdata.readVarInt();
					dataoutputstream.writeShort((id << 4) | id >> 12);
					dataoutputstream.writeShort(coord);
				}
				serializer.writeInt(dataoutputstream.size());
				serializer.writeBytes(bytearrayoutputstream.toByteArray());
				return true;
			}
			case 0x0C: { // PacketPlayOutNamedEntitySpawn
				packet.b(packetdata);
				serializer.writeVarInt(packetdata.readVarInt());
				UUID uuid = packetdata.g();
				serializer.writeString(serializer.getVersion() == 5 ? uuid.toString() : uuid.toString().replace("-", ""));
				String playerName = getTabName(channel.remoteAddress(), uuid);
				serializer.writeString(playerName != null ? playerName : "Unknown");
				if (serializer.getVersion() == 5) {
					serializer.writeVarInt(0);
				}
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readShort());
				serializer.writeBytes(packetdata.readBytes(packetdata.readableBytes()));
				return true;
			}
			case 0x36: { // PacketPlayOutOpenSignEditor
				packet.b(packetdata);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				return true;
			}
			case 0x2D: { // PacketPlayOutOpenWindow
				packet.b(packetdata);
				serializer.writeByte(packetdata.readUnsignedByte());
				byte id = inventoryNameToId.get(packetdata.readString(32));
				serializer.writeByte(id);
				serializer.writeString(packetdata.readString(32));
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeBoolean(true);
				if (id == 11) {
					serializer.writeInt(packetdata.readInt());
				}
				return true;
			}
			case 0x38: { // PacketPlayOutPlayerInfo
				packet.b(packetdata);
				int action = packetdata.readVarInt();
				packetdata.readVarInt();
				UUID uuid = packetdata.g();
				switch (action) {
					case 0: {
						String playerName = packetdata.readString(16);
						addTabName(channel.remoteAddress(), uuid, playerName);
						int props = packetdata.readVarInt();
						for (int p = 0; p < props; p++) {
							packetdata.readString(32767);
							packetdata.readString(32767);
							if (packetdata.readBoolean()) {
								packetdata.readString(32767);
							}
						}
						packetdata.readVarInt();
						packetdata.readVarInt();
						serializer.writeString(playerName);
						serializer.writeBoolean(true);
						serializer.writeShort(0);
						return true;
					}
					case 4: {
						String playerName = getTabName(channel.remoteAddress(), uuid);
						serializer.writeString(playerName);
						serializer.writeBoolean(false);
						serializer.writeShort(0);
						return true;
					}
					default: {
						String playerName = getTabName(channel.remoteAddress(), uuid);
						serializer.writeString(playerName != null ? playerName : "Unknown");
						serializer.writeBoolean(true);
						serializer.writeShort(0);
						return true;
					}
				}
			}
			case 0x08: { // PacketPlayOutPosition
				packet.b(packetdata);
				Player player = ServerConnectionChannel.getPlayer(channel.remoteAddress());
				double x = packetdata.readDouble();
				double y = packetdata.readDouble() + 1.63;
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
				serializer.writeDouble(y);
				serializer.writeDouble(z);
				serializer.writeFloat(yaw);
				serializer.writeFloat(pitch);
				serializer.writeBoolean(false);
				return true;
			}
			case 0x15: { // PacketPlayOutRelEntityMove
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return true;
			}
			case 0x17: { // PacketPlayOutRelEntityMoveLook
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return true;
			}
			case 0x1E: { // PacketPlayOutRemoveEntityEffect
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readUnsignedByte());
				return true;
			}
			case 0x3B: { // PacketPlayOutScoreboardObjective
				packet.b(packetdata);
				serializer.writeString(packetdata.readString(16));
				int mode = serializer.readByte();
				if (mode == 2) {
					mode = 0;
				}
				serializer.writeString(packetdata.readString(32));
				serializer.writeByte(mode);
				return true;
			}
			case 0x3C: { // PacketPlayOutScoreboardScore
				packet.b(packetdata);
				serializer.writeString(packetdata.readString(16));
				int mode = packetdata.readByte();
				serializer.writeByte(mode);
				if (mode != 1) {
					serializer.writeString(packetdata.readString(16));
					serializer.writeInt(packetdata.readVarInt());
				}
				return true;
			}
			case 0x3E: { // PacketPlayOutScoreboardTeam
				packet.b(packetdata);
				serializer.writeString(packetdata.readString(16));
				int mode = packetdata.readByte();
				serializer.writeByte(mode);
				if (mode == 0 || mode == 2) {
					serializer.writeString(packetdata.readString(32));
					serializer.writeString(packetdata.readString(16));
					serializer.writeString(packetdata.readString(16));
					serializer.writeByte(packetdata.readByte());
					packetdata.readString(32);
					packetdata.readByte();
				}
				if (mode == 0 || mode == 3 || mode == 4) {
					int count = packetdata.readVarInt();
					serializer.writeShort(count);
					for (int i = 0; i < count; i++) {
						serializer.writeString(packetdata.readString(40));
					}
				}
				return true;
			}
			case 0x0E: { // PacketPlayOutSpawnObject
				// TODO
				break;
			}
			case 0x10: { // PacketPlayOutSpawnPainting
				packet.b(packetdata);
				serializer.writeVarInt(packetdata.readVarInt());
				serializer.writeString(packetdata.readString(13));
				BlockPosition blockPos = packetdata.c();
				int x = blockPos.getX();
				int z = blockPos.getZ();
				int type = packetdata.readByte();
				switch (type) {
					case 0: {
						--z;
						break;
					}
					case 1: {
						++x;
						break;
					}
					case 2: {
						++z;
						break;
					}
					case 3: {
						--x;
						break;
					}
				}
				serializer.writeInt(x);
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(z);
				serializer.writeInt(type);
				return true;
			}
			case 0x05: { // PacketPlayOutSpawnPosition
				packet.b(packetdata);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				return true;
			}
			case 0x35: { // PacketPlayOutTileEntityData
				packet.b(packetdata);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.a(packetdata.h());
				return true;
			}
			case 0x20: { // PacketPlayOutUpdateAttributes
				packet.b(packetdata);
				serializer.writeInt(packetdata.readVarInt());
				int ascount = packetdata.readInt();
				serializer.writeInt(ascount);
				for (int i = 0; i < ascount; i++) {
					serializer.writeString(packetdata.readString(64));
					serializer.writeDouble(packetdata.readDouble());
					int amcount = packetdata.readVarInt();
					serializer.writeShort(amcount);
					for (int j = 0; j < amcount; j++) {
						serializer.writeLong(packetdata.readLong());
						serializer.writeLong(packetdata.readLong());
						serializer.writeDouble(packetdata.readDouble());
						serializer.writeByte(packetdata.readByte());
					}
				}
				return true;
			}
			case 0x33: { // PacketPlayOutUpdateSign
				packet.b(packetdata);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				for (int i = 0; i < 4; i++) {
					serializer.writeString(CraftChatMessage.fromComponent(packetdata.d()));
				}
				return true;
			}
			case 0x06: { // PacketPlayOutUpdateHealth
				packet.b(packetdata);
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeFloat(packetdata.readFloat());
				return true;
			}
			case 0x28: { // PacketPlayOutWorldEvent
				packet.b(packetdata);
				serializer.writeInt(packetdata.readInt());
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY() & 0xFF);
				serializer.writeInt(blockPos.getZ());
				serializer.writeInt(packetdata.readInt());
				serializer.writeBoolean(packetdata.readBoolean());
				return true;
			}
			case 0x2A: { // PacketPlayOutWorldParticles
				packet.b(packetdata);
				int type = packetdata.readInt();
				serializer.writeString(EnumParticle.values()[type].b());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeInt(packetdata.readInt());
				// TODO: additional data
				return true;
			}
		}
		return false;
	}

	private WeakHashMap<SocketAddress, HashMap<UUID, String>> tabListData = new WeakHashMap<SocketAddress, HashMap<UUID, String>>();

	private void addTabName(SocketAddress address, UUID uuid, String name) {
		if (!tabListData.containsKey(address)) {
			tabListData.put(address, new HashMap<UUID, String>());
		}
		tabListData.get(address).put(uuid, name);
	}

	private String getTabName(SocketAddress address, UUID uuid) {
		if (!tabListData.containsKey(address)) {
			return null;
		}
		return tabListData.get(address).get(uuid);
	}

	@SuppressWarnings("serial")
	private static final HashMap<String, Byte> inventoryNameToId = new HashMap<String, Byte>() {
		{
			put("minecraft:chest", (byte) 0);
			put("minecraft:crafting_table", (byte) 1);
			put("minecraft:furnace", (byte) 2);
			put("minecraft:dispenser", (byte) 3);
			put("minecraft:enchanting_table", (byte) 4);
			put("minecraft:brewing_stand", (byte) 5);
			put("minecraft:villager", (byte) 6);
			put("minecraft:beacon", (byte) 7);
			put("minecraft:anvil", (byte) 8);
			put("minecraft:hopper", (byte) 9);
			put("minecraft:dropper", (byte) 10);
			put("EntityHorse", (byte) 11);
		}
	};

}
