package protocolsupport.protocol.transformer.mcpe.handler;

import gnu.trove.map.TIntObjectMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientboundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.mcpe.PEStorage;
import protocolsupport.protocol.transformer.mcpe.PEStorage.ItemInfo;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.AnimatePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ChatPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ContainerSetSlotPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.EntityEquipmentArmorPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.EntityEquipmentInventoryPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.MovePlayerPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.SetHealthPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddItemEntityPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddLivingEntityPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddPaintingPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AdventureSettingsPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ChunkPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.MoveEntityPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.PickupItemEffectPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.PlayerListPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.PlayerListPacket.PlayerListEntry;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.PongPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.RemoveEntityPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.RemovePlayerPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetBlocksPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetDifficultyPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetTimePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetBlocksPacket.UpdateBlockRecord;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetSpawnPosition;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddPlayerPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.StartGamePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ContainerSetContentsPacket;
import protocolsupport.protocol.transformer.mcpe.utils.PEWatchedPlayer;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedObject;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.Utils;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.World;

public class ClientboundPacketHandler {
	protected static final RemappingTable blockRemapper = IdRemapper.BLOCK.getTable(ProtocolVersion.MINECRAFT_PE);
	protected static final RemappingTable itemRemapper = IdRemapper.ITEM.getTable(ProtocolVersion.MINECRAFT_PE);
	protected static final RemappingTable entityRemapper = IdRemapper.ENTITY.getTable(ProtocolVersion.MINECRAFT_PE);
	protected static final RemappingTable mapcolorRemapper = IdRemapper.MAPCOLOR.getTable(ProtocolVersion.MINECRAFT_PE);

	protected UDPNetworkManager networkManager;
	public ClientboundPacketHandler(UDPNetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	protected int loadedChunkCount;

	private static final long initialChunkCount = Bukkit.getViewDistance() * Bukkit.getViewDistance() * 4;

	private boolean spawned;
	public boolean canSpawnPlayer() {
		return loadedChunkCount >= initialChunkCount && !spawned;
	}

	public void setSpawned() {
		spawned = true;
	}

	protected final LocalStorage storage = new LocalStorage();
	protected final PEStorage pestorage = new PEStorage();

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public List<? extends ClientboundPEPacket> transform(Packet packet) throws Exception {
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.getLatest());
		try {
			packet.b(packetdata);
			switch (EnumProtocol.PLAY.a(EnumProtocolDirection.CLIENTBOUND, packet)) {
				case ClientboundPacket.PLAY_KEEP_ALIVE_ID: {
					return Collections.singletonList(new PongPacket(packetdata.readVarInt()));
				}
				case ClientboundPacket.PLAY_LOGIN_ID: {
					//use this packet to sent needed login packets
					Player bukkitplayer = Utils.getPlayer(networkManager).getBukkitEntity();
					storage.addWatchedSelfPlayer(new WatchedPlayer(bukkitplayer.getEntityId()));
					return Arrays.asList(
						new StartGamePacket(
							bukkitplayer.getWorld().getEnvironment().getId(),
							bukkitplayer.getGameMode().getValue() & 0x1,
							bukkitplayer.getEntityId(),
							bukkitplayer.getWorld().getSpawnLocation(),
							bukkitplayer.getLocation()
						),
						new SetTimePacket((int) bukkitplayer.getWorld().getTime()),
						new SetSpawnPosition(bukkitplayer.getLocation()),
						new SetHealthPacket((int) bukkitplayer.getHealth()),
						new SetDifficultyPacket(bukkitplayer.getWorld().getDifficulty().ordinal()),
						new AdventureSettingsPacket(bukkitplayer.getGameMode() == GameMode.CREATIVE)
					);
				}
				case ClientboundPacket.PLAY_CHAT_ID: {
					return Collections.singletonList(new ChatPacket(LegacyUtils.fromComponent(packetdata.d())));
				}
				case ClientboundPacket.PLAY_UPDATE_TIME_ID: {
					packetdata.readLong();
					return Collections.singletonList(new SetTimePacket((int) packetdata.readLong()));
				}
				case ClientboundPacket.PLAY_UPDATE_HEALTH_ID: {
					return Collections.singletonList(new SetHealthPacket((int) packetdata.readFloat()));
				}
				case ClientboundPacket.PLAY_POSITION_ID: {
					EntityPlayer player = Utils.getPlayer(networkManager);
					double x = packetdata.readDouble();
					double y = packetdata.readDouble();
					double z = packetdata.readDouble();
					float yaw = packetdata.readFloat();
					float pitch = packetdata.readFloat();
					short field = packetdata.readUnsignedByte();
					if ((field & 0x01) != 0) {
						x += player.locX;
					}
					if ((field & 0x02) != 0) {
						y += player.locY;
					}
					if ((field & 0x04) != 0) {
						z += player.locZ;
					}
					if ((field & 0x08) != 0) {
						yaw += player.yaw;
					}
					if ((field & 0x10) != 0) {
						pitch += player.pitch;
					}
					return Collections.singletonList(
						new MovePlayerPacket(
							player.getId(),
							(float) x, (float) y, (float) z,
							yaw, player.aK, pitch, false
						)
					);
				}
				case ClientboundPacket.PLAY_SPAWN_NAMED_ID: {
					int entityId = packetdata.readVarInt();
					UUID uuid = packetdata.readUUID();
					float locX = packetdata.readInt() / 32.0F;
					float locY = packetdata.readInt() / 32.0F;
					float locZ = packetdata.readInt() / 32.0F;
					float yaw = packetdata.readByte() * 360.0F / 256.0F;
					float pitch = packetdata.readByte() * 360.0F / 256.0F;
					String username = storage.getPlayerListName(uuid);
					storage.addWatchedEntity(new PEWatchedPlayer(entityId, uuid));
					return Collections.singletonList(new AddPlayerPacket(
						uuid, username, entityId,
						locX, locY, locZ, yaw, pitch
					));
				}
				case ClientboundPacket.PLAY_COLLECT_EFFECT_ID: {
					int itemId = packetdata.readVarInt();
					int entityId = packetdata.readVarInt();
					return Collections.singletonList(new PickupItemEffectPacket(entityId, itemId));
				}
				case ClientboundPacket.PLAY_SPAWN_OBJECT_ID: {
					int entityId = packetdata.readVarInt();
					int type = packetdata.readByte();
					float locX = packetdata.readInt() / 32.0F;
					float locY = packetdata.readInt() / 32.0F;
					float locZ = packetdata.readInt() / 32.0F;
					packetdata.readByte();
					packetdata.readByte();
					int objdata = packetdata.readInt();
					float speedX = 0;
					float speedY = 0;
					float speedZ = 0;
					if (objdata != 0) {
						speedX = packetdata.readShort() / 8000.0F;
						speedY = packetdata.readShort() / 8000.0F;
						speedZ = packetdata.readShort() / 8000.0F;
					}
					if (type == 2) {
						//only store initial item info, we will spawn item later when metadata will tell us which item is it actually
						pestorage.addItemInfo(entityId, locX, locY, locZ, speedX, speedY, speedZ);
						storage.addWatchedEntity(new WatchedObject(entityId, type));
					}
					return Collections.emptyList();
				}
				case ClientboundPacket.PLAY_SPAWN_LIVING_ID: {
					int entityId = packetdata.readVarInt();
					int type = packetdata.readByte() & 0xFF;
					float x = packetdata.readInt() / 32.0F;
					float y = packetdata.readInt() / 32.0F;
					float z = packetdata.readInt() / 32.0F;
					float yaw = packetdata.readByte() * 360.0F / 256.0F;
					float pitch = packetdata.readByte() * 360.0F / 256.0F;
					return Collections.singletonList(new AddLivingEntityPacket(
						//TODO: not everyone is a zombie
						entityId, 32, x, y, z, yaw, pitch
					));
				}
				case ClientboundPacket.PLAY_SPAWN_PAINTING_ID: {
					int entityId = packetdata.readVarInt();
					String name = packetdata.readString(13);
					BlockPosition location = packetdata.c();
					int direction = packetdata.readByte();
					int x = location.getX();
					int z = location.getZ();
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
					return Collections.singletonList(new AddPaintingPacket(entityId, x, location.getY(), z, direction, name));
				}
				case ClientboundPacket.PLAY_ENTITY_DESTROY_ID: {
					ArrayList<ClientboundPEPacket> packets = new ArrayList<ClientboundPEPacket>();
					int count = packetdata.readVarInt();
					int[] entityIds = new int[count];
					for (int i = 0; i < count; i++) {
						entityIds[i] = packetdata.readVarInt();
					}
					for (int entityId : entityIds) {
						WatchedEntity entity = storage.getWatchedEntity(entityId);
						if (entity == null || entity.getType() != SpecificType.PLAYER) {
							packets.add(new RemoveEntityPacket(entityId));
						} else {
							PEWatchedPlayer peplayer = (PEWatchedPlayer) entity;
							packets.add(new RemovePlayerPacket(entityId, peplayer.getUUID()));
						}
					}
					storage.removeWatchedEntities(entityIds);
					pestorage.removeItemsInfo(entityIds);
					return packets;
				}
				case ClientboundPacket.PLAY_ENTITY_REL_MOVE_ID:
				case ClientboundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID:
				case ClientboundPacket.PLAY_ENTITY_LOOK_ID: {
					int entityId = packetdata.readVarInt();
					Entity entity = Utils.getPlayer(networkManager).world.a(entityId);
					if (entity != null) {
						if (entity instanceof EntityPlayer) {
							return Collections.singletonList(new MovePlayerPacket(
								entityId,
								(float) entity.locX, (float) entity.locY, (float) entity.locZ,
								entity.yaw, ((EntityPlayer) entity).aK, entity.pitch, entity.onGround
							));
						} else {
							return Collections.singletonList(new MoveEntityPacket(
								entityId,
								(float) entity.locX, (float) entity.locY, (float) entity.locZ,
								entity.yaw, entity.pitch
							));
						}
					}
					return Collections.emptyList();
				}
				case ClientboundPacket.PLAY_ENTITY_METADATA_ID: {
					int entityId = packetdata.readVarInt();
					ItemInfo info = pestorage.getItemInfo(entityId);
					if (info != null) {
						TIntObjectMap<DataWatcherObject> metadata = DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, Utils.toArray(packetdata));
						if (metadata.containsKey(10)) {
							return Collections.singletonList(new AddItemEntityPacket(
								entityId, (float) info.getX(), (float) info.getY(), (float) info.getZ(),
								info.getSpeedX(), info.getSpeedY(), info.getSpeedZ(), (ItemStack) metadata.get(10).value
							));
						}
					}
					return Collections.emptyList();
				}
				case ClientboundPacket.PLAY_CHUNK_SINGLE_ID: {
					int x = packetdata.readInt();
					int z = packetdata.readInt();
					boolean cont = packetdata.readBoolean();
					int mask = packetdata.readShort();
					Chunk chunk = Utils.getPlayer(networkManager).getWorld().getChunkIfLoaded(x, z);
					if (chunk != null && !(cont && mask == 0)) {
						loadedChunkCount++;
						return Collections.singletonList(new ChunkPacket(chunk));
					} else {
						return Collections.emptyList();
					}
				}
				case ClientboundPacket.PLAY_BLOCK_CHANGE_MULTI_ID: {
					int chunkX = packetdata.readInt();
					int chunkZ = packetdata.readInt();
					int count = packetdata.readVarInt();
					UpdateBlockRecord[] records = new UpdateBlockRecord[count];
					for (int i = 0; i < count; i++) {
						int xz = packetdata.readUnsignedByte();
						int y = packetdata.readUnsignedByte();
						int stateId = packetdata.e();
						records[i] = new UpdateBlockRecord(
							(chunkX << 4) + (xz >> 4), y, (chunkZ << 4) + (xz & 0xF),
							blockRemapper.getRemap(stateId >> 4), stateId & 0xF,
							SetBlocksPacket.FLAG_ALL_PRIORITY
						);
					}
					return Collections.singletonList(new SetBlocksPacket(records));
				}
				case ClientboundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID: {
					BlockPosition blockPos = packetdata.c();
					int stateId = packetdata.readVarInt();
					return Collections.singletonList(new SetBlocksPacket(new UpdateBlockRecord(
						blockPos.getX(),
						blockPos.getY(),
						blockPos.getZ(),
						blockRemapper.getRemap(stateId >> 4),
						stateId & 0xF,
						SetBlocksPacket.FLAG_ALL_PRIORITY
					)));
				}
				case ClientboundPacket.PLAY_CHUNK_MULTI_ID: {
					packetdata.readBoolean();
					World world = Utils.getPlayer(networkManager).getWorld();
					ArrayList<ChunkPacket> packets = new ArrayList<ChunkPacket>();
					int count = packetdata.readVarInt();
					for (int i = 0; i < count; i++) {
						int x = packetdata.readInt();
						int z = packetdata.readInt();
						packetdata.readShort();
						Chunk chunk = world.getChunkIfLoaded(x, z);
						if (chunk != null) {
							loadedChunkCount++;
							packets.add(new ChunkPacket(chunk));
						}
					}
					return packets;
				}
				case ClientboundPacket.PLAY_WINDOW_SET_SLOT_ID: {
					int windowId = packetdata.readByte();
					int slot = packetdata.readShort();
					ItemStack itemstack = packetdata.readItemStack();
					itemstack = PEPlayerInventory.addSlotNumberTag(itemstack, slot);
					//TODO: other inventory types
					if (windowId == 0) {
						if (slot >= 9 && slot < 45) {
							return Collections.singletonList(new ContainerSetSlotPacket(PEPlayerInventory.PLAYER_INVENTORY_WID, slot - 9, itemstack));
						} else if (slot >= 5 && slot < 9) {
							return Collections.singletonList(new ContainerSetSlotPacket(PEPlayerInventory.PLAYER_ARMOR_WID, slot - 5, itemstack));
						}
					}
					return Collections.emptyList();
				}
				case ClientboundPacket.PLAY_WINDOW_SET_ITEMS_ID: {
					EntityPlayer player = Utils.getPlayer(networkManager);
					int windowId = packetdata.readByte();
					ItemStack[] packetitems = new ItemStack[packetdata.readShort()];
					for (int i = 0; i < packetitems.length; i++) {
						packetitems[i] = PEPlayerInventory.addSlotNumberTag(packetdata.readItemStack(), i);
					}
					//TODO: other inventory types
					if (windowId == 0) {
						ArrayList<ContainerSetContentsPacket> packets = new ArrayList<ContainerSetContentsPacket>();
						ItemStack[] inventory = new ItemStack[45];
						System.arraycopy(packetitems, 9, inventory, 0, inventory.length - ContainerSetContentsPacket.HOTBAR_SLOTS.length);
						packets.add(new ContainerSetContentsPacket(PEPlayerInventory.PLAYER_INVENTORY_WID, inventory, ((PEPlayerInventory) player.inventory).getHotbarRefs()));
						ItemStack[] armor = new ItemStack[4];
						System.arraycopy(packetitems, 5, armor, 0, armor.length);
						packets.add(new ContainerSetContentsPacket(PEPlayerInventory.PLAYER_ARMOR_WID, armor, ContainerSetContentsPacket.EMPTY_HOTBAR_SLOTS));
						return packets;
					}
					return Collections.emptyList();
				}
				case ClientboundPacket.PLAY_PLAYER_INFO_ID: {
					int action = packetdata.readVarInt();
					int count = packetdata.readVarInt();
					HashSet<UUID> uuids = new HashSet<UUID>();
					for (int i = 0; i < count; i++) {
						UUID uuid = packetdata.readUUID();
						uuids.add(uuid);
						switch (action) {
							case 0: {
								String username = packetdata.readString(16);
								storage.addPlayerListName(uuid, username);
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
								break;
							}
							case 4: {
								storage.removePlayerListName(uuid);
								break;
							}
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
					switch (action) {
						case 4: {
							ArrayList<PlayerListEntry> entries = new ArrayList<PlayerListEntry>();
							for (UUID uuid : uuids) {
								entries.add(new PlayerListEntry(uuid));
							}
							return Collections.singletonList(new PlayerListPacket(false, entries));
						}
						case 0: {
							ArrayList<PlayerListEntry> entries = new ArrayList<PlayerListEntry>();
							for (UUID uuid : uuids) {
								entries.add(new PlayerListEntry(uuid, storage.getPlayerListName(uuid)));
							}
							return Collections.singletonList(new PlayerListPacket(true, entries));
						}
					}
					return Collections.emptyList();
				}
				case ClientboundPacket.PLAY_ENTITY_EQUIPMENT_ID: {
					int entityId = packetdata.readVarInt();
					int slot = packetdata.readShort();
					ItemStack itemstack = packetdata.readItemStack();
					if (slot == 0) {
						return Collections.singletonList(new EntityEquipmentInventoryPacket(entityId, itemstack));
					} else {
						pestorage.setArmorSlot(entityId, slot - 1, itemstack);
						return Collections.singletonList(new EntityEquipmentArmorPacket(entityId, Utils.reverseArray(pestorage.getArmor(entityId))));
					}
				}
				/*case ClientboundPacket.PLAY_ANIMATION_ID: {
					int entityId = packetdata.readVarInt();
					int action = packetdata.readByte();
					return Collections.singletonList(new AnimatePacket(action, entityId));
				}*/
				default: {
					return Collections.emptyList();
				}
			}
		} finally {
			packetdata.release();
		}
	}

}
