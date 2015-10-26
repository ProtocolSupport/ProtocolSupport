package protocolsupport.protocol.transformer.mcpe.handler;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.mcpe.ItemInfoStorage;
import protocolsupport.protocol.transformer.mcpe.ItemInfoStorage.ItemInfo;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ChatPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ContainerSetSlotPacket;
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
import protocolsupport.protocol.transformer.mcpe.utils.BlockIDRemapper;
import protocolsupport.protocol.transformer.mcpe.utils.EntityRemapper;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedObject;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.ClassToFuncMapper;
import protocolsupport.utils.ClassToFuncMapper.Func;
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
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutCollect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowItems;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayOutLogin;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_8_R3.World;

public class ClientboundPacketHandler {

	private static final ClassToFuncMapper<ClientboundPacketHandler, List<? extends ClientboundPEPacket>, PacketDataSerializer> transformers = new ClassToFuncMapper<>();
	static {
		transformers.register(PacketPlayOutKeepAlive.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) {
				return Collections.singletonList(new PongPacket(packetdata.readVarInt()));
			}
		});
		transformers.register(PacketPlayOutLogin.class, new TransformFunc() {
			@SuppressWarnings("deprecation")
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) {
				//use this packet to sent needed login packets
				Player bukkitplayer = Utils.getPlayer(scope.networkManager).getBukkitEntity();
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
		});
		transformers.register(PacketPlayOutChat.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws IOException {
				return Collections.singletonList(new ChatPacket(LegacyUtils.fromComponent(packetdata.d())));
			}
		});
		transformers.register(PacketPlayOutUpdateTime.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) {
				packetdata.readLong();
				return Collections.singletonList(new SetTimePacket((int) packetdata.readLong()));
			}
		});
		transformers.register(PacketPlayOutUpdateHealth.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				return Collections.singletonList(new SetHealthPacket((int) packetdata.readFloat()));
			}
		});
		transformers.register(PacketPlayOutPosition.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				EntityPlayer player = Utils.getPlayer(scope.networkManager);
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
		});
		transformers.register(PacketPlayOutNamedEntitySpawn.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int entityId = packetdata.readVarInt();
				UUID uuid = packetdata.readUUID();
				float locX = packetdata.readInt() / 32.0F;
				float locY = packetdata.readInt() / 32.0F;
				float locZ = packetdata.readInt() / 32.0F;
				float yaw = packetdata.readByte() * 360.0F / 256.0F;
				float pitch = packetdata.readByte() * 360.0F / 256.0F;
				String username = scope.storage.getPlayerListName(uuid);
				scope.storage.addWatchedEntity(new WatchedPlayer(entityId));
				scope.playerUUIDs.put(entityId, uuid);
				return Collections.singletonList(new AddPlayerPacket(
					uuid, username, entityId,
					locX, locY, locZ, yaw, pitch
				));
			}
		});
		transformers.register(PacketPlayOutCollect.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int itemId = packetdata.readVarInt();
				int entityId = packetdata.readVarInt();
				return Collections.singletonList(new PickupItemEffectPacket(entityId, itemId));
			}
		});
		transformers.register(PacketPlayOutSpawnEntity.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
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
					scope.itemInfo.addItemInfo(entityId, locX, locY, locZ, speedX, speedY, speedZ);
					scope.storage.addWatchedEntity(new WatchedObject(entityId, type));
				}
				return Collections.emptyList();
			}
		});
		transformers.register(PacketPlayOutSpawnEntityLiving.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int entityId = packetdata.readVarInt();
				int type = packetdata.readByte() & 0xFF;
				float x = packetdata.readInt() / 32.0F;
				float y = packetdata.readInt() / 32.0F;
				float z = packetdata.readInt() / 32.0F;
				float yaw = packetdata.readFloat();
				float pitch = packetdata.readFloat();
				/*if (spawned) {
					return Collections.singletonList(new AddLivingEntityPacket(
						entityId, 32, x, y, z, yaw, pitch
					));
				}*/
				return Collections.emptyList();
			}
		});
		transformers.register(PacketPlayOutSpawnEntityPainting.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
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
		});
		transformers.register(PacketPlayOutEntityDestroy.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				ArrayList<ClientboundPEPacket> packets = new ArrayList<ClientboundPEPacket>();
				int count = packetdata.readVarInt();
				int[] entityIds = new int[count];
				for (int i = 0; i < count; i++) {
					entityIds[i] = packetdata.readVarInt();
				}
				for (int entityId : entityIds) {
					WatchedEntity entity = scope.storage.getWatchedEntity(entityId);
					if (entity == null || entity.getType() != SpecificType.PLAYER) {
						packets.add(new RemoveEntityPacket(entityId));
					} else {
						UUID uuid = scope.playerUUIDs.get(entityId);
						if (uuid != null) {
							packets.add(new RemovePlayerPacket(entityId, uuid));
						}
					}
				}
				scope.storage.removeWatchedEntities(entityIds);
				scope.itemInfo.removeItemsInfo(entityIds);
				return packets;
			}
		});
		TransformFunc relmoveTr = new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int entityId = packetdata.readVarInt();
				Entity entity = Utils.getPlayer(scope.networkManager).world.a(entityId);
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
		};
		transformers.register(PacketPlayOutRelEntityMove.class, relmoveTr);
		transformers.register(PacketPlayOutRelEntityMoveLook.class, relmoveTr);
		transformers.register(PacketPlayOutEntityLook.class, relmoveTr);
		transformers.register(PacketPlayOutEntityMetadata.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int entityId = packetdata.readVarInt();
				ItemInfo info = scope.itemInfo.getItemInfo(entityId);
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
		});
		transformers.register(PacketPlayOutMapChunk.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int x = packetdata.readInt();
				int z = packetdata.readInt();
				boolean cont = packetdata.readBoolean();
				int mask = packetdata.readShort();
				Chunk chunk = Utils.getPlayer(scope.networkManager).getWorld().getChunkIfLoaded(x, z);
				if (chunk != null && !(cont && mask == 0)) {
					scope.loadedChunkCount++;
					return Collections.singletonList(new ChunkPacket(chunk));
				} else {
					return Collections.emptyList();
				}
			}
		});
		transformers.register(PacketPlayOutMultiBlockChange.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
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
						BlockIDRemapper.replaceBlockId(stateId >> 4), stateId & 0xF,
						SetBlocksPacket.FLAG_ALL_PRIORITY
					);
				}
				return Collections.singletonList(new SetBlocksPacket(records));
			}
		});
		transformers.register(PacketPlayOutBlockChange.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				BlockPosition blockPos = packetdata.c();
				int stateId = packetdata.readVarInt();
				return Collections.singletonList(new SetBlocksPacket(new UpdateBlockRecord(
					blockPos.getX(),
					blockPos.getY(),
					blockPos.getZ(),
					BlockIDRemapper.replaceBlockId(stateId >> 4),
					stateId & 0xF,
					SetBlocksPacket.FLAG_ALL_PRIORITY
				)));
			}
		});
		transformers.register(PacketPlayOutMapChunkBulk.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				packetdata.readBoolean();
				World world = Utils.getPlayer(scope.networkManager).getWorld();
				ArrayList<ChunkPacket> packets = new ArrayList<ChunkPacket>();
				int count = packetdata.readVarInt();
				for (int i = 0; i < count; i++) {
					int x = packetdata.readInt();
					int z = packetdata.readInt();
					packetdata.readShort();
					Chunk chunk = world.getChunkIfLoaded(x, z);
					if (chunk != null) {
						scope.loadedChunkCount++;
						packets.add(new ChunkPacket(chunk));
					}
				}
				return packets;
			}
		});
		transformers.register(PacketPlayOutSetSlot.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int windowId = packetdata.readByte();
				int slot = packetdata.readShort();
				ItemStack itemstack = packetdata.readItemStack();
				itemstack = PEPlayerInventory.addFakeTag(itemstack);
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
		});
		transformers.register(PacketPlayOutWindowItems.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int windowId = packetdata.readByte();
				ItemStack[] packetitems = new ItemStack[packetdata.readShort()];
				for (int i = 0; i < packetitems.length; i++) {
					packetitems[i] = PEPlayerInventory.addFakeTag(packetdata.readItemStack());
				}
				//TODO: other inventory types
				if (windowId == 0) {
					ArrayList<ContainerSetContentsPacket> packets = new ArrayList<ContainerSetContentsPacket>();
					ItemStack[] inventory = new ItemStack[45];
					System.arraycopy(packetitems, 9, inventory, 0, inventory.length - ContainerSetContentsPacket.HOTBAR_SLOTS.length);
					packets.add(new ContainerSetContentsPacket(PEPlayerInventory.PLAYER_INVENTORY_WID, inventory, ContainerSetContentsPacket.HOTBAR_SLOTS));
					ItemStack[] armor = new ItemStack[4];
					System.arraycopy(packetitems, 5, armor, 0, armor.length);
					packets.add(new ContainerSetContentsPacket(PEPlayerInventory.PLAYER_ARMOR_WID, armor, ContainerSetContentsPacket.EMPTY_HOTBAR_SLOTS));
					return packets;
				}
				return Collections.emptyList();
			}
		});
		transformers.register(PacketPlayOutPlayerInfo.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int action = packetdata.readVarInt();
				int count = packetdata.readVarInt();
				HashSet<UUID> uuids = new HashSet<UUID>();
				for (int i = 0; i < count; i++) {
					UUID uuid = packetdata.readUUID();
					uuids.add(uuid);
					switch (action) {
						case 0: {
							String username = packetdata.readString(16);
							scope.storage.addPlayerListName(uuid, username);
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
							scope.storage.removePlayerListName(uuid);
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
							entries.add(new PlayerListEntry(uuid, scope.storage.getPlayerListName(uuid)));
						}
						return Collections.singletonList(new PlayerListPacket(true, entries));
					}
				}
				return Collections.emptyList();
			}
		});
		transformers.register(PacketPlayOutEntityEquipment.class, new TransformFunc() {
			@Override
			public List<? extends ClientboundPEPacket> run(ClientboundPacketHandler scope, PacketDataSerializer packetdata) throws Exception {
				int entityId = packetdata.readVarInt();
				int slot = packetdata.readShort();
				if (slot == 0) {
					ItemStack itemstack = packetdata.readItemStack();
					return Collections.singletonList(new EntityEquipmentInventoryPacket(entityId, itemstack));
				}
				return Collections.emptyList();
			}
		});
		transformers.stopAcceptingRegistrations();
	}

	private abstract static class TransformFunc extends Func<ClientboundPacketHandler, List<? extends ClientboundPEPacket>, PacketDataSerializer> {
	}

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

	//TODO: rework storage
	protected final LocalStorage storage = new LocalStorage();
	protected final ItemInfoStorage itemInfo = new ItemInfoStorage();
	protected final TIntObjectMap<UUID> playerUUIDs = new TIntObjectHashMap<UUID>();

	@SuppressWarnings("rawtypes")
	public List<? extends ClientboundPEPacket> transform(Packet packet) throws Exception {
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_1_8);
		try {
			packet.b(packetdata);
			List<? extends ClientboundPEPacket> result = transformers.runFunc(packet.getClass(), this, packetdata);
			if (result != null) {
				return result;
			} else {
				return Collections.emptyList();
			}
		} finally {
			packetdata.release();
		}
	}

}
