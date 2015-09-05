package protocolsupport.protocol.transformer.v_1_5.clientboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.Deflater;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.transformer.utils.MapTransformer;
import protocolsupport.protocol.transformer.utils.MapTransformer.ColumnEntry;
import protocolsupport.protocol.transformer.v_1_5.remappers.BlockIDRemapper;
import protocolsupport.protocol.transformer.v_1_5.remappers.EntityIDRemapper;
import protocolsupport.protocol.transformer.v_1_5.remappers.ItemIDRemapper;
import protocolsupport.protocol.transformer.v_1_5.remappers.MapColorRemapper;
import protocolsupport.protocol.transformer.v_1_5.utils.ChunkUtils;
import protocolsupport.protocol.transformer.v_1_5.utils.VillagerTradeTransformer;
import protocolsupport.protocol.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.watchedentity.types.WatchedLiving;
import protocolsupport.protocol.watchedentity.types.WatchedObject;
import protocolsupport.protocol.watchedentity.types.WatchedPlayer;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.Utils;

public class PlayPacketTransformer implements PacketTransformer {

	private WatchedPlayer player;
	private LocalStorage storage = new LocalStorage();

	@Override
	public void tranform(Channel channel, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_1_8);
		packet.b(packetdata);
		switch (packetId) {
			case 0x00: { //PacketPlayOutKeepAlive
				serializer.writeByte(0x00);
				serializer.writeInt(packetdata.readVarInt());
				break;
			}
			case 0x01: { //PacketPlayOutLogin
				serializer.writeByte(0x01);
				int playerEnityId = packetdata.readInt();
				player = new WatchedPlayer(playerEnityId);
				storage.addWatchedEntity(player);
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
				break;
			}
			case 0x02: { //PacketPlayOutChat
				serializer.writeByte(0x03);
				serializer.writeString(LegacyUtils.fromComponent(packetdata.d()));
				break;
			}
			case 0x03: { //PacketPlayOutUpdateTime
				serializer.writeByte(0x04);
				serializer.writeLong(packetdata.readLong());
				serializer.writeLong(packetdata.readLong());
				break;
			}
			case 0x04: { //PacketPlayOutEntityEquipment
				serializer.writeByte(0x05);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readShort());
				serializer.writeItemStack(packetdata.readItemStack());
				break;
			}
			case 0x05: { //PacketPlayOutSpawnPosition
				serializer.writeByte(0x06);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY() + 1);
				serializer.writeInt(blockPos.getZ());
				break;
			}
			case 0x06: { //PacketPlayOutUpdateHealth
				serializer.writeByte(0x08);
				serializer.writeShort((int) packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeFloat(packetdata.readFloat());
				break;
			}
			case 0x07: { //PacketPlayOutRespawn
				storage.clearWatchedEntities();
				if (player != null) {
					storage.addWatchedEntity(player);
				}
				serializer.writeByte(0x09);
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(256);
				serializer.writeString(packetdata.readString(32767));
				break;
			}
			case 0x08: { // PacketPlayOutPosition
				serializer.writeByte(0x0D);
				Player player = Utils.getPlayer(channel);
				double x = packetdata.readDouble();
				double y = packetdata.readDouble() + 1.6200000047683716D;
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
				serializer.writeDouble(y - 1.6200000047683716D);
				serializer.writeDouble(z);
				serializer.writeFloat(yaw);
				serializer.writeFloat(pitch);
				serializer.writeBoolean(false);
				break;
			}
			case 0x09: { //PacketPlayOutHeldItemSlot
				serializer.writeByte(0x10);
				serializer.writeShort(packetdata.readByte());
				break;
			}
			case 0x0A: { //PacketPlayOutBed
				serializer.writeByte(0x11);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(0);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				break;
			}
			case 0x0B: { //PacketPlayOutAnimation
				serializer.writeByte(0x12);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte() + 1);
				break;
			}
			case 0x0C: { //PacketPlayOutNamedEntitySpawn
				serializer.writeByte(0x14);
				int playerEntityId = packetdata.readVarInt();
				storage.addWatchedEntity(new WatchedPlayer(playerEntityId));
				serializer.writeInt(playerEntityId);
				UUID uuid = packetdata.g();
				String playerName = storage.getPlayerListName(uuid);
				serializer.writeString(playerName != null ? playerName : "Unknown");
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readShort());
				serializer.writeBytes(
					DataWatcherSerializer.encodeData(
						serializer.getVersion(),
						WatchedDataRemapper.transform(
							storage.getWatchedEntity(playerEntityId),
							DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, Utils.toArray(packetdata)),
							serializer.getVersion()
						)
					)
				);
				break;
			}
			case 0x0D: { //PacketPlayOutCollect
				serializer.writeByte(0x16);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readVarInt());
				break;
			}
			case 0x0E: { //PacketPlayOutSpawnEntity
				int entityId = packetdata.readVarInt();
				int type = packetdata.readUnsignedByte();
				storage.addWatchedEntity(new WatchedObject(entityId, type));
				if (type == 78) { //skip armor stands
					break;
				}
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
					objectdata = (id | (data << 16));
				}
				if ((type == 50) || (type == 70) || (type == 74)) {
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
				break;
			}
			case 0x0F: { //PacketPlayOutSpawnEntityLiving
				int entityId = packetdata.readVarInt();
				int type = packetdata.readUnsignedByte();
				storage.addWatchedEntity(new WatchedLiving(entityId, type));
				if (type == 30) { //skip armor stands
					break;
				}
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
				serializer.writeBytes(
					DataWatcherSerializer.encodeData(
						serializer.getVersion(),
						WatchedDataRemapper.transform(
							storage.getWatchedEntity(entityId),
							DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, Utils.toArray(packetdata)),
							serializer.getVersion()
						)
					)
				);
				break;
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
				break;
			}
			case 0x11: { //PacketPlayOutSpawnEntityExperienceOrb
				serializer.writeByte(0x1A);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeShort(packetdata.readShort());
				break;
			}
			case 0x12: { //PacketPlayOutEntityVelocity
				serializer.writeByte(0x1C);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				break;
			}
			case 0x13: { //PacketPlayOutEntityDestroy
				int count = packetdata.readVarInt();
				int[] array = new int[count];
				for (int i = 0; i < count; i++) {
					array[i] = packetdata.readVarInt();
				}
				storage.removeWatchedEntities(array);
				if (player != null) {
					storage.addWatchedEntity(player);
				}
				for (int[] part : Utils.splitArray(array, 120)) {
					serializer.writeByte(0x1D);
					serializer.writeByte(part.length);
					for (int i = 0; i < part.length; i++) {
						serializer.writeInt(part[i]);
					}
				}
				break;
			}
			case 0x14: { //PacketPlayOutEntity
				serializer.writeByte(0x1E);
				serializer.writeInt(packetdata.readVarInt());
				break;
			}
			case 0x15: { //PacketPlayOutRelEntityMove
				serializer.writeByte(0x1F);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x16: { //PacketPlayOutEntityLook
				serializer.writeByte(0x20);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x17: { //PacketPlayOutRelEntityMoveLook
				serializer.writeByte(0x21);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x18: { //PacketPlayOutEntityTeleport
				serializer.writeByte(0x22);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x19: { //PacketPlayOutEntityHeadRotation
				serializer.writeByte(0x23);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x1A: { //PacketPlayOutEntityStatus
				serializer.writeByte(0x26);
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x1B: { //PacketPlayOutAttachEntity
				serializer.writeByte(0x27);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				break;
			}
			case 0x1C: { //PacketPlayOutEntityMetadata
				serializer.writeByte(0x28);
				int entityId = packetdata.readVarInt();
				serializer.writeInt(entityId);
				serializer.writeBytes(
					DataWatcherSerializer.encodeData(
						serializer.getVersion(),
						WatchedDataRemapper.transform(
							storage.getWatchedEntity(entityId),
							DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, Utils.toArray(packetdata)),
							serializer.getVersion()
						)
					)
				);
				break;
			}
			case 0x1D: { //PacketPlayOutEntityEffect
				int entityId = packetdata.readVarInt();
				int effectId = packetdata.readByte();
				if ((effectId >= 21) && (effectId <= 23)) {
					break;
				}
				serializer.writeByte(0x29);
				serializer.writeInt(entityId);
				serializer.writeByte(effectId);
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readVarInt());
				break;
			}
			case 0x1E: { //PacketPlayOutRemoveEntityEffect
				int entityId = packetdata.readVarInt();
				int effectId = packetdata.readByte();
				if ((effectId >= 21) && (effectId <= 23)) {
					break;
				}
				serializer.writeByte(0x2A);
				serializer.writeInt(entityId);
				serializer.writeByte(effectId);
				break;
			}
			case 0x1F: { //PacketPlayOutExperience
				serializer.writeByte(0x2B);
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeShort(packetdata.readVarInt());
				break;
			}
			case 0x21: { //PacketPlayOutMapChunk
				serializer.writeByte(0x33);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeBoolean(packetdata.readBoolean());
				int bitmap = packetdata.readShort() & 0xFFFF;
				serializer.writeShort(bitmap);
				serializer.writeShort(0);
				byte[] data = ChunkUtils.to15ChunkData(packetdata.readArray(), bitmap);
				final Deflater deflater = new Deflater(Deflater.BEST_SPEED);
				deflater.setInput(data, 0, data.length);
				deflater.finish();
				byte[] networkdata = new byte[data.length + 200];
				int size = deflater.deflate(networkdata);
				deflater.end();
				serializer.writeInt(size);
				serializer.writeBytes(networkdata, 0, size);
				break;
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
				break;
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
				break;
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
				break;
			}
			case 0x25: { //PacketPlayOutBlockBreakAnimation
				serializer.writeByte(0x37);
				serializer.writeInt(packetdata.readVarInt());
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getY());
				serializer.writeByte(packetdata.readByte());
				break;
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
					ByteBuf chunkdata = packetdata.readBytes(ChunkUtils.calcDataSize(Integer.bitCount(bitmap[i]), skylight, true));
					try {
						data[i] = ChunkUtils.to15ChunkData(Utils.toArray(chunkdata), bitmap[i]);
						pos += data[i].length;
					} finally {
						chunkdata.release();
					}
				}
				ldata = new byte[pos];
				pos = 0;
				for (int i = 0; i < data.length; i++) {
					System.arraycopy(data[i], 0, ldata, pos, data[i].length);
					pos += data[i].length;
				}
				// compress
				final Deflater deflater = new Deflater(Deflater.BEST_SPEED);
				deflater.setInput(ldata, 0, ldata.length);
				deflater.finish();
				byte[] networkdata = new byte[ldata.length + 200];
				int size = deflater.deflate(networkdata);
				deflater.end();
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
				break;
			}
			case 0x27: { //PacketPlayOutExplosion
				serializer.writeByte(0x3C);
				serializer.writeDouble(packetdata.readFloat());
				serializer.writeDouble(packetdata.readFloat());
				serializer.writeDouble(packetdata.readFloat());
				serializer.writeBytes(packetdata);
				break;
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
				break;
			}
			case 0x29: { //PacketPlayOutNamedSoundEffect
				serializer.writeByte(0x3E);
				String name = LegacyUtils.getSound(packetdata.readString(32767));
				serializer.writeString(name);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x2A: { //PacketPlayOutWorldParticles
				serializer.writeByte(0x3F);
				EnumParticle particle = EnumParticle.values()[packetdata.readInt()];
				String particlename = particle.b();
				packetdata.readBoolean();
				float x = packetdata.readFloat();
				float y = packetdata.readFloat();
				float z = packetdata.readFloat();
				float addx = packetdata.readFloat();
				float addy = packetdata.readFloat();
				float addz = packetdata.readFloat();
				float speed = packetdata.readFloat();
				int count = packetdata.readInt();
				switch (particle) {
					case ITEM_CRACK: {
						int itemid = packetdata.readVarInt();
						particlename += ItemIDRemapper.replaceItemId(itemid);
						break;
					}
					case BLOCK_CRACK:
					case BLOCK_DUST: {
						int blockstateId = packetdata.readVarInt();
						particlename = "tilecrack_" + BlockIDRemapper.replaceBlockId((blockstateId & 4095)) + "_" + ((blockstateId >> 12) & 0xF);
						break;
					}
					default: {
						break;
					}
				}
				serializer.writeString(particlename);
				serializer.writeFloat(x);
				serializer.writeFloat(y);
				serializer.writeFloat(z);
				serializer.writeFloat(addx);
				serializer.writeFloat(addy);
				serializer.writeFloat(addz);
				serializer.writeFloat(speed);
				serializer.writeInt(count);
				break;
			}
			case 0x2B: { //PacketPlayOutGameStateChange
				serializer.writeByte(0x46);
				byte value = packetdata.readByte();
				switch (value) {
					case 1: {
						value = 2;
						break;
					}
					case 2: {
						value = 1;
						break;
					}
					default: {
						break;
					}
				}
				serializer.writeByte(value);
				serializer.writeByte((int) packetdata.readFloat());
				break;
			}
			case 0x2C: { //PacketPlayOutSpawnEntityWeather
				serializer.writeByte(0x47);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				break;
			}
			case 0x2D: { //PacketPlayOutOpenWindow
				int windowId = packetdata.readUnsignedByte();
				byte id = LegacyUtils.getInventoryId(packetdata.readString(32));
				//fiter out horse inventory
				if (id == 11) {
					Utils.getPlayer(channel).closeInventory();
					break;
				}
				serializer.writeByte(0x64);
				serializer.writeByte(windowId);
				serializer.writeByte(id);
				serializer.writeString(LegacyUtils.fromComponent(packetdata.d()));
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeBoolean(true);
				break;
			}
			case 0x2E: { //PacketPlayOutCloseWindow
				serializer.writeByte(0x65);
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x2F: { //PacketPlayOutSetSlot
				byte windowId = packetdata.readByte();
				int slot = packetdata.readShort();
				if (Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					if (slot == 1) {
						break;
					}
					if (slot > 0) {
						slot--;
					}
				}
				serializer.writeByte(0x67);
				serializer.writeByte(windowId);
				serializer.writeShort(slot);
				serializer.writeItemStack(packetdata.readItemStack());
				break;
			}
			case 0x30: { //PacketPlayOutWindowItems
				serializer.writeByte(0x68);
				boolean isEnchanting = Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING;
				serializer.writeByte(packetdata.readByte());
				int count = packetdata.readShort();
				serializer.writeShort(isEnchanting ? count - 1 : count);
				for (int i = 0; i < count; i++) {
					ItemStack item = packetdata.readItemStack();
					if (isEnchanting && i == 1) {
						continue;
					}
					serializer.writeItemStack(item);
				}
				break;
			}
			case 0x31: { //PacketPlayOutWindowData
				serializer.writeByte(0x69);
				serializer.writeByte(packetdata.readByte());
				int type = packetdata.readShort();
				if (Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.FURNACE) {
					switch (type) {
						case 0: {
							type = 1;
							break;
						}
						case 1: {
							type = 2;
							break;
						}
						case 2: {
							type = 0;
							break;
						}
						default: {
							break;
						}
					}
				}
				serializer.writeShort(type);
				serializer.writeShort(packetdata.readShort());
				break;
			}
			case 0x32: { //PacketPlayOutTransaction
				serializer.writeByte(0x6A);
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readShort());
				serializer.writeBoolean(packetdata.readBoolean());
				break;
			}
			case 0x33: { //PacketPlayOutUpdateSign
				serializer.writeByte(0x82);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				for (int i = 0; i < 4; i++) {
					serializer.writeString(Utils.clampString(LegacyUtils.fromComponent(packetdata.d()), 15));
				}
				break;
			}
			case 0x34: { //PacketPlayOutMap
				packet.b(packetdata);
				int itemData = packetdata.readVarInt();
				int scale = packetdata.readByte();
				//send scale
				serializer.writeByte(0x83);
				serializer.writeShort(358);
				serializer.writeShort(itemData);
				serializer.writeShort(2);
				serializer.writeByte(2);
				serializer.writeByte(scale);
				//send icons
				int icons = packetdata.readVarInt();
				if (icons > 0) {
					serializer.writeByte(0x83);
					serializer.writeShort(358);
					serializer.writeShort(itemData);
					serializer.writeShort(icons * 3 + 1);
					serializer.writeByte(1);
					serializer.writeBytes(packetdata.readBytes(icons * 3));
				}
				//send columns
				int columns = packetdata.readUnsignedByte();
				if (columns > 0) {
					int rows = packetdata.readUnsignedByte();
					int xstart = packetdata.readUnsignedByte();
					int ystart = packetdata.readUnsignedByte();
					byte[] data = new byte[packetdata.readVarInt()];
					packetdata.readBytes(data);
					MapTransformer maptransformer = new MapTransformer();
					maptransformer.loadFromNewMapData(columns, rows, xstart, ystart, data);
					for (ColumnEntry entry : maptransformer.transformToOldMapData()) {
						serializer.writeByte(0x83);
						serializer.writeShort(358);
						serializer.writeShort(itemData);
						serializer.writeShort(3 + entry.getColors().length);
						serializer.writeByte(0);
						serializer.writeByte(entry.getX());
						serializer.writeByte(entry.getY());
						byte[] colors = entry.getColors();
						for (int i = 0; i < colors.length; i++) {
							colors[i] = MapColorRemapper.replaceMapColor(colors[i]);
						}
						serializer.writeBytes(colors);
					}
				}
				break;
			}
			case 0x35: { //PacketPlayOutTileEntityData
				serializer.writeByte(0x84);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				int id = packetdata.readByte();
				serializer.writeByte(id);
				NBTTagCompound compound = packetdata.h();
				if (id == 1) {
					compound.remove("SpawnPotentials");
					compound.remove("SpawnData");
				}
				serializer.a(compound);
				break;
			}
			case 0x38: { //PacketPlayOutPlayerInfo
				int action = packetdata.readVarInt();
				int count = packetdata.readVarInt();
				for (int i = 0; i < count; i++) {
					UUID uuid = packetdata.g();
					switch (action) {
						case 0: {
							String playerName = packetdata.readString(16);
							storage.addPlayerListName(uuid, playerName);
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
							String playerName = storage.getPlayerListName(uuid);
							if (playerName != null) {
								storage.removePlayerListName(uuid);
								serializer.writeByte(0xC9);
								serializer.writeString(playerName);
								serializer.writeBoolean(false);
								serializer.writeShort(0);
							}
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
				break;
			}
			case 0x39: { //PacketPlayOutAbilities
				serializer.writeByte(0xCA);
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte((int) (packetdata.readFloat() * 255.0F));
				serializer.writeByte((int) (packetdata.readFloat() * 255.0F));
				break;
			}
			case 0x3A: { //PacketPlayOutTabComplete
				int count = packetdata.readVarInt();
				if (count == 0) {
					break;
				}
				serializer.writeByte(0xCB);
				StringBuilder builder = new StringBuilder();
				builder.append(packetdata.readString(32767));
				while (--count > 0) {
					builder.append("\u0000");
					builder.append(packetdata.readString(32767));
				}
				serializer.writeString(builder.toString());
				break;
			}
			case 0x3B: { //PacketPlayOutScoreboardObjective
				serializer.writeByte(0xCE);
				serializer.writeString(packetdata.readString(16));
				int mode = packetdata.readByte();
				if (mode == 1) {
					serializer.writeString("");
					serializer.writeByte(mode);
					break;
				}
				serializer.writeString(packetdata.readString(32));
				serializer.writeByte(mode);
				break;
			}
			case 0x3C: { //PacketPlayOutScoreboardScore
				serializer.writeByte(0xCF);
				serializer.writeString(Utils.clampString(packetdata.readString(40), 16));
				int mode = packetdata.readByte();
				serializer.writeByte(mode);
				if (mode != 1) {
					serializer.writeString(packetdata.readString(16));
					serializer.writeInt(packetdata.readVarInt());
				}
				break;
			}
			case 0x3D: { //PacketPlayOutScoreboardDisplayObjective
				serializer.writeByte(0xD0);
				serializer.writeByte(packetdata.readByte());
				serializer.writeString(packetdata.readString(32767));
				break;
			}
			case 0x3E: { //PacketPlayOutScoreboardTeam
				serializer.writeByte(0xD1);
				serializer.writeString(packetdata.readString(16));
				int mode = packetdata.readByte();
				serializer.writeByte(mode);
				if ((mode == 0) || (mode == 2)) {
					serializer.writeString(packetdata.readString(32));
					serializer.writeString(packetdata.readString(16));
					serializer.writeString(packetdata.readString(16));
					serializer.writeByte(packetdata.readByte());
					packetdata.readString(32);
					packetdata.readByte();
				}
				if ((mode == 0) || (mode == 3) || (mode == 4)) {
					int count = packetdata.readVarInt();
					serializer.writeShort(count);
					for (int i = 0; i < count; i++) {
						serializer.writeString(packetdata.readString(32767));
					}
				}
				break;
			}
			case 0x3F: { //PacketPlayOutCustomPayload
				serializer.writeByte(0xFA);
				String tag = packetdata.readString(20);
				serializer.writeString(tag);
				//fix villager trade list
				if (tag.equals("MC|TrList")) {
					byte[] data = VillagerTradeTransformer.to15VillagerTradeList(packetdata, serializer.getVersion());
					serializer.writeShort(data.length);
					serializer.writeBytes(data);
					break;
				}
				serializer.writeShort(packetdata.readableBytes());
				serializer.writeBytes(packetdata);
				break;
			}
			case 0x40: { //PacketPlayOutKickDisconnect
				serializer.writeByte(0xFF);
				serializer.writeString(LegacyUtils.fromComponent(packetdata.d()));
				break;
			}
		}
		packetdata.release();
	}

}
