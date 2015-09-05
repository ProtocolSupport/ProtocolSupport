package protocolsupport.protocol.transformer.v_1_7.clientboundtransformer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.Deflater;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.ItemStack;
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
import protocolsupport.protocol.transformer.v_1_7.remappers.BlockIDRemapper;
import protocolsupport.protocol.transformer.v_1_7.remappers.EntityIDRemapper;
import protocolsupport.protocol.transformer.v_1_7.remappers.ItemIDRemapper;
import protocolsupport.protocol.transformer.v_1_7.utils.ChunkUtils;
import protocolsupport.protocol.transformer.v_1_7.utils.VillagerTradeTransformer;
import protocolsupport.protocol.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.watchedentity.types.WatchedLiving;
import protocolsupport.protocol.watchedentity.types.WatchedObject;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.Utils;

import com.mojang.authlib.properties.Property;

public class PlayPacketTransformer implements PacketTransformer {

	private LocalStorage storage = new LocalStorage();

	@Override
	public void tranform(ChannelHandlerContext ctx, int packetId, Packet<PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		Channel channel = ctx.channel();
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_1_8);
		switch (packetId) {
			case 0x00: { // PacketPlayOutKeepAlive
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				break;
			}
			case 0x01: { // PacketPlayOutLogin
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				int maxplayers = packetdata.readUnsignedByte();
				serializer.writeByte(Math.min(maxplayers, 60));
				serializer.writeString(packetdata.readString(16));
				break;
			}
			case 0x02: { // PacketPlayOutChat
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.a(ChatSerializer.a(packetdata.d()));
				break;
			}
			case 0x04: { // PacketPlayOutEntityEquipment
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readShort());
				serializer.writeItemStack(packetdata.readItemStack());
				break;
			}
			case 0x05: { // PacketPlayOutSpawnPosition
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				break;
			}
			case 0x06: { // PacketPlayOutUpdateHealth
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeFloat(packetdata.readFloat());
				break;
			}
			case 0x07: { //PacketPlayOutRespawn
				storage.clearWatchedEntities();
				serializer.writeVarInt(packetId);
				packet.b(serializer);
				break;
			}
			case 0x08: { // PacketPlayOutPosition
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				Player player = Utils.getPlayer(channel);
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
				break;
			}
			case 0x0A: { // PacketPlayOutBed
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				break;
			}
			case 0x0C: { // PacketPlayOutNamedEntitySpawn
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeVarInt(packetdata.readVarInt());
				UUID uuid = packetdata.g();
				serializer.writeString(serializer.getVersion() == ProtocolVersion.MINECRAFT_1_7_10 ? uuid.toString() : uuid.toString().replace("-", ""));
				String playerName = storage.getPlayerListName(uuid);
				serializer.writeString(playerName != null ? playerName : "Unknown");
				if (serializer.getVersion() == ProtocolVersion.MINECRAFT_1_7_10) {
					ArrayList<Property> properties = storage.getPropertyData(uuid, true);
					serializer.writeVarInt(properties.size());
					for (Property property : properties) {
						serializer.writeString(property.getName());
						serializer.writeString(property.getValue());
						serializer.writeString(property.getSignature());
					}
				}
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeShort(packetdata.readUnsignedShort());
				serializer.writeBytes(packetdata);
				break;
			}
			case 0x0D: { // PacketPlayOutCollect
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeInt(packetdata.readVarInt());
				break;
			}
			case 0x0E: { // PacketPlayOutSpawnEntity
				packet.b(packetdata);
				int entityId = packetdata.readVarInt();
				int type = packetdata.readUnsignedByte();
				storage.addWatchedEntity(new WatchedObject(entityId, type));
				if (type == 78) { //skip armor stand
					break;
				}
				serializer.writeVarInt(packetId);
				serializer.writeVarInt(entityId);
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
				packet.b(packetdata);
				int entityId = packetdata.readVarInt();
				int type = packetdata.readUnsignedByte();
				storage.addWatchedEntity(new WatchedLiving(entityId, type));
				if (type == 30) { //skip armor stands
					break;
				}
				serializer.writeVarInt(packetId);
				serializer.writeVarInt(entityId);
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
			case 0x10: { // PacketPlayOutSpawnPainting
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeVarInt(packetdata.readVarInt());
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
			case 0x12: { // PacketPlayOutEntityVelocity
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				serializer.writeShort(packetdata.readShort());
				break;
			}
			case 0x13: { // PacketPlayOutEntityDestroy
				packet.b(packetdata);
				PacketDataSerializer writePacketData = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
				int count = packetdata.readVarInt();
				int[] array = new int[count];
				for (int i = 0; i < count; i++) {
					array[i] = packetdata.readVarInt();
				}
				storage.removeWatchedEntities(array);
				for (int[] part : Utils.splitArray(array, 120)) {
					writePacketData.clear();
					writePacketData.writeVarInt(packetId);
					writePacketData.writeByte(part.length);
					for (int i = 0; i < part.length; i++) {
						writePacketData.writeInt(part[i]);
					}
					ctx.write(writePacketData.copy());
				}
				break;
			}
			case 0x14: { // PacketPlayOutEntity
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				break;
			}
			case 0x15: { // PacketPlayOutRelEntityMove
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x16: { // PacketPlayOutEntityLook
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x17: { // PacketPlayOutRelEntityMoveLook
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x18: { // PacketPlayOutEntityTeleport
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				int entityId = packetdata.readVarInt();
				serializer.writeInt(entityId);
				serializer.writeInt(packetdata.readInt());
				int y = packetdata.readInt();
				WatchedEntity entity = storage.getWatchedEntity(entityId);
				if ((entity != null) && (entity.getType() == SpecificType.TNT || entity.getType() == SpecificType.FALLING_OBJECT)) {
					y += 16;
				}
				serializer.writeInt(y);
				serializer.writeInt(packetdata.readInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x19: { // PacketPlayOutEntityHeadRotation
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				break;
			}
			case 0x1C: { // PacketPlayOutEntityMetadata
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
			case 0x1D: { // PacketPlayOutEntityEffect
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readByte());
				serializer.writeByte(packetdata.readByte());
				serializer.writeShort(packetdata.readVarInt());
				break;
			}
			case 0x1E: { // PacketPlayOutRemoveEntityEffect
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readVarInt());
				serializer.writeByte(packetdata.readUnsignedByte());
				break;
			}
			case 0x1F: { // PacketPlayOutExperience
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeFloat(packetdata.readFloat());
				serializer.writeShort(packetdata.readVarInt());
				serializer.writeShort(packetdata.readVarInt());
				break;
			}
			case 0x20: { // PacketPlayOutUpdateAttributes
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
				break;
			}
			case 0x21: { // PacketPlayOutMapChunk
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readInt());
				serializer.writeInt(packetdata.readInt());
				serializer.writeBoolean(packetdata.readBoolean());
				int bitmap = packetdata.readShort() & 0xFFFF;
				serializer.writeShort(bitmap);
				serializer.writeShort(0);
				byte[] data = ChunkUtils.to17ChunkData(packetdata.readArray(), bitmap);
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
			case 0x22: { // PacketPlayOutMultiBlockChange
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
			case 0x23: { // PacketPlayOutBlockChange
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				int stateId = packetdata.readVarInt();
				serializer.writeVarInt(BlockIDRemapper.replaceBlockId(stateId >> 4));
				serializer.writeByte(stateId & 0xF);
				break;
			}
			case 0x24: { // PacketPlayOutBlockAction
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeVarInt(packetdata.readVarInt());
				break;
			}
			case 0x25: { // PacketPlayOutBlockBreakAnimation
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeVarInt(packetdata.readVarInt());
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				serializer.writeByte(packetdata.readUnsignedByte());
				break;
			}
			case 0x26: { // PacketPlayOutMapChunkBulk
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
						data[i] = ChunkUtils.to17ChunkData(Utils.toArray(chunkdata), bitmap[i]);
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
				Deflater deflater = new Deflater(Deflater.BEST_SPEED);
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
			case 0x28: { // PacketPlayOutWorldEvent
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeInt(packetdata.readInt());
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeByte(blockPos.getY() & 0xFF);
				serializer.writeInt(blockPos.getZ());
				serializer.writeInt(packetdata.readInt());
				serializer.writeBoolean(packetdata.readBoolean());
				break;
			}
			case 0x2A: { // PacketPlayOutWorldParticles
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
						particlename += BlockIDRemapper.replaceBlockId((blockstateId & 4095)) + "_" + ((blockstateId >> 12) & 0xF);
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
			case 0x2D: { // PacketPlayOutOpenWindow
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeByte(packetdata.readUnsignedByte());
				byte id = LegacyUtils.getInventoryId(packetdata.readString(32));
				serializer.writeByte(id);
				serializer.writeString(LegacyUtils.fromComponent(packetdata.d()));
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.writeBoolean(true);
				if (id == 11) {
					serializer.writeInt(packetdata.readInt());
				}
				break;
			}
			case 0x2F: { //PacketPlayOutSetSlot
				if (Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					packet.b(packetdata);
					byte windowId = packetdata.readByte();
					int slot = packetdata.readShort();
					if (slot == 1) {
						break;
					}
					if (slot > 0) {
						slot--;
					}
					serializer.writeVarInt(packetId);
					serializer.writeByte(windowId);
					serializer.writeShort(slot);
					serializer.writeItemStack(packetdata.readItemStack());
					break;
				} else {
					serializer.writeVarInt(packetId);
					packet.b(serializer);
					break;
				}
			}
			case 0x30: { //PacketPlayOutWindowItems
				serializer.writeVarInt(packetId);
				if (Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.ENCHANTING) {
					packet.b(packetdata);
					serializer.writeByte(packetdata.readByte());
					int count = packetdata.readShort();
					serializer.writeShort(count - 1);
					for (int i = 0; i < count; i++) {
						ItemStack item = packetdata.readItemStack();
						if (i == 1) {
							continue;
						}
						serializer.writeItemStack(item);
					}
					break;
				} else {
					packet.b(serializer);
					break;
				}
			}
			case 0x31: { //PacketPlayOutWindowData
				serializer.writeVarInt(packetId);
				if (Utils.getPlayer(channel).getOpenInventory().getType() == InventoryType.FURNACE) {
					packet.b(packetdata);
					serializer.writeByte(packetdata.readByte());
					int type = packetdata.readShort();
					switch (type) {
						case 0: {
							serializer.writeShort(1);
							break;
						}
						case 1: {
							serializer.writeShort(2);
							break;
						}
						case 2: {
							serializer.writeShort(0);
							break;
						}
						default: {
							serializer.writeShort(type);
							break;
						}
					}
					serializer.writeShort(packetdata.readShort());
					break;
				} else {
					packet.b(serializer);
					break;
				}
			}
			case 0x33: { // PacketPlayOutUpdateSign
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				for (int i = 0; i < 4; i++) {
					serializer.writeString(Utils.clampString(LegacyUtils.fromComponent(packetdata.d()), 15));
				}
				break;
			}
			case 0x34: { // PacketPlayOutMap
				PacketDataSerializer writePacketData = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
				packet.b(packetdata);
				int itemData = packetdata.readVarInt();
				int scale = packetdata.readByte();
				//send scale
				writePacketData.writeVarInt(packetId);
				writePacketData.writeVarInt(itemData);
				writePacketData.writeShort(2);
				writePacketData.writeByte(2);
				writePacketData.writeByte(scale);
				ctx.write(writePacketData.copy());
				//send icons
				int icons = packetdata.readVarInt();
				if (icons > 0) {
					writePacketData.clear();
					writePacketData.writeVarInt(packetId);
					writePacketData.writeVarInt(itemData);
					writePacketData.writeShort(icons * 3 + 1);
					writePacketData.writeByte(1);
					writePacketData.writeBytes(packetdata.readBytes(icons * 3));
					ctx.write(writePacketData.copy());
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
						writePacketData.clear();
						writePacketData.writeVarInt(packetId);
						writePacketData.writeVarInt(itemData);
						writePacketData.writeShort(3 + entry.getColors().length);
						writePacketData.writeByte(0);
						writePacketData.writeByte(entry.getX());
						writePacketData.writeByte(entry.getY());
						writePacketData.writeBytes(entry.getColors());
						ctx.write(writePacketData.copy());
					}
				}
				break;
			}
			case 0x35: { // PacketPlayOutTileEntityData
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeShort(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				serializer.writeByte(packetdata.readUnsignedByte());
				serializer.a(packetdata.h());
				break;
			}
			case 0x36: { // PacketPlayOutOpenSignEditor
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				BlockPosition blockPos = packetdata.c();
				serializer.writeInt(blockPos.getX());
				serializer.writeInt(blockPos.getY());
				serializer.writeInt(blockPos.getZ());
				break;
			}
			case 0x38: { // PacketPlayOutPlayerInfo
				packet.b(packetdata);
				PacketDataSerializer writePacketData = new PacketDataSerializer(Unpooled.buffer(), serializer.getVersion());
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
								String name = packetdata.readString(32767);
								String value = packetdata.readString(32767);
								String signature = null;
								if (packetdata.readBoolean()) {
									signature = packetdata.readString(32767);
								}
								storage.addPropertyData(uuid, signature != null ? new Property(name, value, signature) : new Property(value, name));
							}
							packetdata.readVarInt();
							packetdata.readVarInt();
							if (packetdata.readBoolean()) {
								packetdata.d();
							}
							writePacketData.clear();
							writePacketData.writeVarInt(packetId);
							writePacketData.writeString(playerName);
							writePacketData.writeBoolean(true);
							writePacketData.writeShort(0);
							ctx.write(writePacketData.copy());
							break;
						}
						case 4: {
							String playerName = storage.getPlayerListName(uuid);
							if (playerName != null) {
								storage.removePlayerListName(uuid);
								storage.removePropertyData(uuid);
								writePacketData.clear();
								writePacketData.writeVarInt(packetId);
								writePacketData.writeString(playerName);
								writePacketData.writeBoolean(false);
								writePacketData.writeShort(0);
								ctx.write(writePacketData.copy());
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
						default: {
							break;
						}
					}
				}
				break;
			}
			case 0x3B: { // PacketPlayOutScoreboardObjective
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
			case 0x3C: { // PacketPlayOutScoreboardScore
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				serializer.writeString(Utils.clampString(packetdata.readString(40), 16));
				int mode = packetdata.readByte();
				serializer.writeByte(mode);
				if (mode != 1) {
					serializer.writeString(packetdata.readString(16));
					serializer.writeInt(packetdata.readVarInt());
				}
				break;
			}
			case 0x3E: { // PacketPlayOutScoreboardTeam
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
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
						serializer.writeString(packetdata.readString(40));
					}
				}
				break;
			}
			case 0x3F: { // PacketPlayOutCustomPayload
				packet.b(packetdata);
				serializer.writeVarInt(packetId);
				String tag = packetdata.readString(20);
				serializer.writeString(tag);
				//fix villager trade list
				if (tag.equals("MC|TrList")) {
					byte[] data = VillagerTradeTransformer.to17VillagerTradeList(packetdata, serializer.getVersion());
					serializer.writeShort(data.length);
					serializer.writeBytes(data);
					break;
				}
				serializer.writeShort(packetdata.readableBytes());
				serializer.writeBytes(packetdata);
				break;
			}
			case 0x48: { //PacketPlayOutResourcePackSend
				packet.b(packetdata);
				//remap to custom payload send with resource pack tag
				serializer.writeVarInt(0x3F);
				serializer.writeString("MC|RPack");
				byte[] urldata = packetdata.readString(32767).getBytes(StandardCharsets.UTF_8);
				serializer.writeShort(urldata.length);
				serializer.writeBytes(urldata);
				break;
			}
			default: { //Any other packet
				serializer.writeVarInt(packetId);
				packet.b(serializer);
				break;
			}
		}
		packetdata.release();
	}

}
