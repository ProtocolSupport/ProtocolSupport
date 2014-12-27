package protocolsupport.protocol.v_1_5.clientboundtransformer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.Deflater;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.v_1_5.remappers.BlockIDRemapper;
import protocolsupport.protocol.v_1_5.remappers.EntityIDRemapper;
import protocolsupport.protocol.watchedentites.WatchedEntity;
import protocolsupport.utils.Utils;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Packet;

public class PlayPacketTransformer implements PacketTransformer {

	private TIntObjectHashMap<WatchedEntity> watchedEntities = new TIntObjectHashMap<WatchedEntity>();

	private void addWatchedEntity(WatchedEntity entity) {
		watchedEntities.put(entity.getId(), entity);
	}

	private WatchedEntity getWatchedEntity(int entityId) {
		return watchedEntities.get(entityId);
	}

	private void removeWatchedEntities(int[] entityIds) {
		for (int entityId : entityIds) {
			watchedEntities.remove(entityId);
		}
	}

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
				int playerEnityId = packetdata.readInt();
				addWatchedEntity(new WatchedEntity(playerEnityId));
				serializer.writeInt(playerEnityId);
				int gamemode = packetdata.readByte();
				int dimension = packetdata.readByte();
				int difficulty = packetdata.readByte();
				int maxplayers = packetdata.readByte();
				serializer.writeString(packetdata.readString(32767));
				serializer.writeByte(gamemode);
				serializer.writeByte(dimension);
				serializer.writeByte(difficulty);
				serializer.writeByte(0);
				serializer.writeByte(Math.min(Math.abs(maxplayers), 60));
				return;
			}
			case 0x02: { //PacketPlayOutChat
				serializer.writeByte(0x03);
				serializer.writeString(CraftChatMessage.fromComponent(packetdata.d()));
				return;
			}
			case 0x03: { //PacketPlayOutUpdateTime
				serializer.writeByte(0x04);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x04: { //PacketPlayOutEntityEquipment
				serializer.writeByte(0x05);
				serializer.writeInt(packetdata.readVarInt());
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x05: { //PacketPlayOutSpawnPosition
				serializer.writeByte(0x06);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY() + 1);
				serializer.writeInt(blockPos.getZ());
				return;
			}
			case 0x06: { //PacketPlayOutUpdateHealth
				serializer.writeByte(0x08);
				serializer.writeShort((int) packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeFloat(packetdata.readFloat());
				return;
			}
			case 0x07: { //PacketPlayOutRespawn
				serializer.writeByte(0x09);
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(256);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x08: { // PacketPlayOutPosition
				serializer.writeByte(0x0D);
				Player player = DataStorage.getPlayer(channel.remoteAddress());
				double x = packetdata.readDouble();
				double y = packetdata.readDouble() + 1.63;
				double z = packetdata.readDouble();
				float yaw = packetdata.readFloat();
				float pitch = packetdata.readFloat();
				short field = packetdata.readByte();
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
				serializer.writeDouble(y + 1.63);
				serializer.writeDouble(z);
				serializer.writeFloat(yaw);
				serializer.writeFloat(pitch);
				serializer.writeBoolean(false);
				return;
			}
			case 0x09: { //PacketPlayOutHeldItemSlot
				serializer.writeByte(0x10);
				serializer.writeShort(packetdata.readByte());
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
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x0C: { //PacketPlayOutNamedEntitySpawn
				serializer.writeByte(0x14);
				int playerEntityId = packetdata.readVarInt();
				addWatchedEntity(new WatchedEntity(playerEntityId));
				serializer.writeInt(playerEntityId);
				UUID uuid = packetdata.g();
				String playerName = DataStorage.getTabName(channel.remoteAddress(), uuid);
				serializer.writeString(playerName != null ? playerName : "Unknown");
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readShort());
				serializer.writeBytes(DataWatcherFilter.filterEntityData(serializer.getVersion(), getWatchedEntity(playerEntityId), packetdata.readBytes(packetdata.readableBytes()).array()));
				return;
			}
			case 0x0D: { //PacketPlayOutCollect
				serializer.writeByte(0x16);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readVarInt());
				return;
			}
			case 0x0E: { //PacketPlayOutSpawnEntity
				int entityId = packetdata.readVarInt();
				int type = packetdata.readUnsignedByte();
				if (type == 78) { //skip armor stands
					return;
				}
				addWatchedEntity(new WatchedEntity(entityId, type));
				serializer.writeByte(0x17);
				serializer.writeInt(entityId);
				serializer.writeByte(type);
				int x = packetdata.readInt();
				int y = packetdata.readInt();
				int z = packetdata.readInt();
				int pitch = packetdata.readUnsignedByte();
				int yaw = packetdata.readUnsignedByte();
				int objectdata = packetdata.readInt();
		        if (type == 71) {
		            switch (objectdata) {
		                case 0: {
		                    z -= 32;
		                    yaw = 128;
		                    break;
		                }
		                case 1: {
		                    x += 32;
		                    yaw = 64;
		                    break;
		                }
		                case 2: {
		                    z += 32;
		                    yaw = 0;
		                    break;
		                }
		                case 3: {
		                    x -= 32;
		                    yaw = 192;
		                    break;
		                }
		            }
		        }
		        if (type == 70) {
		            final int id = objectdata & 0xFFFF;
		            final int data = objectdata >> 12;
		            objectdata = (id | data << 16);
		        }
		        if (type == 50 || type == 70 || type == 74) {
		            y += 16;
		        }
		        serializer.writeInt(x);
		        serializer.writeInt(y);
		        serializer.writeInt(z);
		        serializer.writeByte(pitch);
		        serializer.writeByte(yaw);
		        serializer.writeInt(objectdata);
		        if (objectdata > 0) {
		        	serializer.writeShort(packetdata.readShort());
		        	serializer.writeShort(packetdata.readShort());
		        	serializer.writeShort(packetdata.readShort());
		        }
				return;
			}
			case 0x0F: { //PacketPlayOutSpawnEntityLiving
				int entityId = packetdata.readVarInt();
				int type = packetdata.readUnsignedByte();
				if (type == 30) { //skip armor stands
					return;
				}
				addWatchedEntity(new WatchedEntity(entityId, type));
				serializer.writeByte(0x18);
				serializer.writeInt(entityId);
				serializer.writeByte(EntityIDRemapper.replaceLivingEntityId(type));
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				serializer.writeBytes(DataWatcherFilter.filterEntityData(serializer.getVersion(), getWatchedEntity(entityId), packetdata.readBytes(packetdata.readableBytes()).array()));
				return;
			}
			case 0x10: { //PacketPlayOutSpawnEntityPainting
				serializer.writeByte(0x19);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeString(packetdata.readString(13));
				BlockPosition blockPos = packetdata.c();
				int x = blockPos.getX();
				int z = blockPos.getZ();
				int direction = packetdata.readByte();
				switch (direction) {
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
				serializer.writeInt(direction);
				return;
			}
			case 0x11: { //PacketPlayOutSpawnEntityExperienceOrb
				serializer.writeByte(0x1A);
				serializer.writeInt(packetdata.readVarInt());
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x12: { //PacketPlayOutEntityVelocity
				serializer.writeByte(0x1C);
				serializer.writeInt(packetdata.readVarInt());
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x13: { //PacketPlayOutEntityDestroy
				int count = packetdata.readVarInt();
				int[] array = new int[count];
				for (int i = 0; i < count; i++) {
					array[i] = packetdata.readVarInt();
				}
				removeWatchedEntities(array);
				for (int[] part : Utils.splitArray(array, 120)) {
					serializer.writeByte(0x1D);
					serializer.writeByte(part.length);
					for (int i = 0; i < part.length; i++) {
						serializer.writeInt(part[i]);
					}
				}
				return;
			}
			case 0x14: { //PacketPlayOutEntity
				serializer.writeByte(0x1E);
				serializer.writeInt(packetdata.readVarInt());
				return;
			}
			case 0x15: { //PacketPlayOutRelEntityMove
				serializer.writeByte(0x1F);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x16: { //PacketPlayOutEntityLook
				serializer.writeByte(0x20);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x17: { //PacketPlayOutRelEntityMoveLook
				serializer.writeByte(0x21);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x18: { //PacketPlayOutEntityTeleport
				serializer.writeByte(0x22);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x19: { //PacketPlayOutEntityHeadRotation
				serializer.writeByte(0x23);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x1A: { //PacketPlayOutEntityStatus
				serializer.writeByte(0x26);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x1B: { //PacketPlayOutAttachEntity
				serializer.writeByte(0x27);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				return;
			}
			case 0x1C: { //PacketPlayOutEntityMetadata
				serializer.writeByte(0x28);
				int entityId = packetdata.readVarInt();
				serializer.writeInt(entityId);
				serializer.writeBytes(DataWatcherFilter.filterEntityData(serializer.getVersion(), getWatchedEntity(entityId), packetdata.readBytes(packetdata.readableBytes()).array()));
				return;
			}
			case 0x1D: { //PacketPlayOutEntityEffect
				int entityId = packetdata.readVarInt();
				int effectId = packetdata.readByte();
				if (effectId >= 21 && effectId <= 23) {
					return;
				}
				serializer.writeByte(0x29);
				serializer.writeInt(entityId);
				serializer.writeByte(effectId);
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readVarInt());
				return;
			}
			case 0x1E: { //PacketPlayOutRemoveEntityEffect
				int entityId = packetdata.readVarInt();
				int effectId = packetdata.readByte();
				if (effectId >= 21 && effectId <= 23) {
					return;
				}
				serializer.writeByte(0x2A);
				serializer.writeInt(entityId);
				serializer.writeByte(effectId);
				return;
			}
			case 0x1F: { //PacketPlayOutExperience
				serializer.writeByte(0x2B);
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeShort(packetdata.readVarInt());
				return;
			}
			case 0x21: { //PacketPlayOutMapChunk
				serializer.writeByte(0x33);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeBoolean(packetdata.readBoolean());
				int bitmap = packetdata.readShort() & 0xFFFF;
				serializer.writeShort(bitmap);
				serializer.writeShort(0);
				byte[] data = ChunkUtils.to15ChunkData(packetdata.a(), bitmap);
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
				return;
			}
			case 0x22: { //PacketPlayOutMultiBlockChange
				serializer.writeByte(0x34);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				int count = packetdata.readVarInt();
				serializer.writeShort(count);
				final ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(count * 4);
				final DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
				for (int i = 0; i < count; i++) {
					dataoutputstream.writeShort(packetdata.readUnsignedShort());
					int id = packetdata.readVarInt();
					dataoutputstream.writeShort((BlockIDRemapper.replaceBlockId(id >> 4) << 4) | (id & 0xF));
				}
				serializer.writeInt(dataoutputstream.size());
				serializer.writeBytes(bytearrayoutputstream.toByteArray());
				return;
			}
			case 0x23: { //PacketPlayOutBlockChange
				serializer.writeByte(0x35);	
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				int stateId = packetdata.readVarInt();
				serializer.writeShort(BlockIDRemapper.replaceBlockId(stateId >> 4));
				serializer.writeByte(stateId & 0xF);
				return;
			}
			case 0x24: { //PacketPlayOutBlockAction
				serializer.writeByte(0x36);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeShort(packetdata.readVarInt());
				return;
			}
			case 0x25: { //PacketPlayOutBlockBreakAnimation
				serializer.writeByte(0x37);
				serializer.writeInt(packetdata.readVarInt());
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getY());
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x26: { //PacketPlayOutMapChunkBulk
				serializer.writeByte(0x38);
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
					data[i] = ChunkUtils.to15ChunkData(packetdata.readBytes(ChunkUtils.calcDataSize(Integer.bitCount(bitmap[i]), skylight, true)).array(), bitmap[i]);
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
				return;
			}
			case 0x27: { //PacketPlayOutExplosion
				serializer.writeByte(0x3C);
				serializer.writeDouble(packetdata.readFloat());
				serializer.writeDouble(packetdata.readFloat());
				serializer.writeDouble(packetdata.readFloat());
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x28: { //PacketPlayOutWorldEvent
				serializer.writeByte(0x3D);
				int type = packetdata.readInt();
				serializer.writeInt(type);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				int data = packetdata.readInt();
				if (type == 2001) {
					data = BlockIDRemapper.replaceBlockId(data & 0xFFF);
				}
				serializer.writeInt(data);
				serializer.writeBoolean(packetdata.readBoolean());
				return;
			}
			case 0x29: { //PacketPlayOutNamedSoundEffect
				serializer.writeByte(0x3E);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x2A: { //PacketPlayOutWorldParticles
				serializer.writeByte(0x3F);
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
				return;
			}
			case 0x2B: { //PacketPlayOutGameStateChange
				serializer.writeByte(0x46);
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte((int) packetdata.readFloat());
				return;
			}
			case 0x2C: { //PacketPlayOutSpawnEntityWeather
				serializer.writeByte(0x47);
				serializer.writeInt(packetdata.readVarInt());
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x2D: { //PacketPlayOutOpenWindow
				serializer.writeByte(0x64);
				serializer.writeByte(packetdata.readUnsignedByte());
				byte id = Utils.getInventoryId(packetdata.readString(32));
				serializer.writeByte(id);
				serializer.writeString(packetdata.d().getText());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeBoolean(true);
				if (id == 11) {
					serializer.writeInt(packetdata.readInt());
				}
				return;
			}
			case 0x2E: { //PacketPlayOutCloseWindow
				serializer.writeByte(0x65);
				serializer.writeByte(packetdata.readByte());
				return;
			}
			case 0x2F: { //PacketPlayOutSetSlot
				if (DataStorage.getPlayer(channel.remoteAddress()).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					byte windowId = packetdata.readByte();
					int slot = packetdata.readShort();
					if (slot == 1) {
						return;
					}
					if (slot > 0) {
						slot--;
					}
					serializer.writeByte(0x67);
					serializer.writeByte(windowId);
					serializer.writeShort(slot);
					serializer.a(packetdata.i());
					return;
				} else {
					serializer.writeByte(0x67);
					Utils.writeTheRestOfTheData(packetdata, serializer);
					return;
				}
			}
			case 0x30: { //PacketPlayOutWindowItems
				serializer.writeByte(0x68);
				if (DataStorage.getPlayer(channel.remoteAddress()).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					serializer.writeByte(packetdata.readByte());
					int count = packetdata.readShort();
					serializer.writeShort(count - 1);
					for (int i = 0; i < count; i++) {
						ItemStack item = packetdata.i();
						if (i == 1) {
							continue;
						}
						serializer.a(item);
					}
					return;
				} else {
					Utils.writeTheRestOfTheData(packetdata, serializer);
					return;
				}
			}
			case 0x31: { //PacketPlayOutWindowData
				serializer.writeByte(0x69);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x32: { //PacketPlayOutTransaction
				serializer.writeByte(0x6A);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x33: { //PacketPlayOutUpdateSign
				serializer.writeByte(0x82);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				for (int i = 0; i < 4; i++) {
					serializer.writeString(Utils.clampString(CraftChatMessage.fromComponent(packetdata.d()), 15));
				}
				return;
			}
			case 0x34: { //PacketPlayOutMap
				//TODO
				return;
			}
			case 0x35: { //PacketPlayOutTileEntityData
				serializer.writeByte(0x84);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x36: { //PacketPlayOutOpenSignEditor
				serializer.writeByte(0x85);
				serializer.writeByte(0);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				return;
			}
			case 0x37: { //PacketPlayOutStatistic
				//TODO, will probably have to split them to multiple packets in packetencoder
				return;
			}
			case 0x38: { //PacketPlayOutPlayerInfo
				int action = packetdata.readVarInt();
				int count = packetdata.readVarInt();
				for (int i = 0; i < count; i++) {
					UUID uuid = packetdata.g();
					switch (action) {
						case 0: {
							String playerName = packetdata.readString(16);
							DataStorage.addTabName(channel.remoteAddress(), uuid, playerName);
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
							if (packetdata.readBoolean()) {
								packetdata.d();
							}
							serializer.writeByte(0xC9);
							serializer.writeString(playerName);
							serializer.writeBoolean(true);
							serializer.writeShort(0);
							break;
						}
						case 4: {
							String playerName = DataStorage.getTabName(channel.remoteAddress(), uuid);
							serializer.writeByte(0xC9);
							serializer.writeString(playerName);
							serializer.writeBoolean(false);
							serializer.writeShort(0);
							break;
						}
						//don't send packet, but still read data
						case 1: {
							packetdata.readVarInt();
							break;
						}
						case 2: {
							packetdata.readVarInt();
							break;
						}
						case 3: {
							if (packetdata.readBoolean()) {
								packetdata.d();
							}
							break;
						}
					}
				}
				return;
			}
			case 0x39: { //PacketPlayOutAbilities
				serializer.writeByte(0xCA);
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte((int) (packetdata.readFloat() * 255.0F));
				serializer.writeByte((int) (packetdata.readFloat() * 255.0F));
				return;
			}
			case 0x3A: { //PacketPlayOutTabComplete
				int count = packetdata.readVarInt();
				if (count == 0) {
					return;
				}
				serializer.writeByte(0xCB);
				StringBuilder builder = new StringBuilder();
				builder.append(packetdata.readString(32767));
				while (--count > 0) {
					builder.append("\u0000");
					builder.append(packetdata.readString(32767));
				}
				serializer.writeString(builder.toString());
				return;
			}
			case 0x3B: { //PacketPlayOutScoreboardObjective
				serializer.writeByte(0xCE);
				serializer.writeString(packetdata.readString(16));
				int mode = packetdata.readByte();
				if (mode == 1) {
					serializer.writeString("");
					serializer.writeByte(mode);
					return;
				}
				if (mode == 2) {
					mode = 0;
				}
				serializer.writeString(packetdata.readString(32));
				serializer.writeByte(mode);
				return;
			}
			case 0x3C: { //PacketPlayOutScoreboardScore
				serializer.writeByte(0xCF);
				serializer.writeString(packetdata.readString(16));
				int mode = packetdata.readByte();
				serializer.writeByte(mode);
				if (mode != 1) {
					serializer.writeString(packetdata.readString(16));
					serializer.writeInt(packetdata.readVarInt());
				}
				return;
			}
			case 0x3D: { //PacketPlayOutScoreboardDisplayObjective
				serializer.writeByte(0xD0);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
			case 0x3E: { //PacketPlayOutScoreboardTeam
				serializer.writeByte(0xD1);
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
					Utils.writeTheRestOfTheData(packetdata, serializer);
				}
				return;
			}
			case 0x3F: { //PacketPlayOutCustomPayload
				serializer.writeByte(0xFA);
				serializer.writeString(packetdata.readString(20));
				ByteBuf data = packetdata.readBytes(packetdata.readableBytes());
				serializer.writeShort(data.readableBytes());
				serializer.writeBytes(data);
				return;
			}
			case 0x40: { //PacketPlayOutKickDisconnect
				serializer.writeByte(0xFF);
				Utils.writeTheRestOfTheData(packetdata, serializer);
				return;
			}
		}
	}

}
