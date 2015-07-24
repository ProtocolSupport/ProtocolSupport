package protocolsupport.protocol.transformer.mcpe.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.ChatPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.MovePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.SetHealthPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ChunkPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.PongPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetBlocksPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetBlocksPacket.UpdateBlockRecord;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.SetTimePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.StartGamePacket;
import protocolsupport.protocol.transformer.mcpe.remapper.BlockIDRemapper;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.Utils;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.World;

public class ClientboundPacketHandler {

	private UDPNetworkManager networkManager;
	public ClientboundPacketHandler(UDPNetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	private int loadedChunkCount;
	private boolean spawned;

	public boolean canSpawnPlayer() {
		return loadedChunkCount >= 60 && !spawned;
	}

	public void setSpawned() {
		spawned = true;
	}

	private LinkedList<ChunkPacket> chunkQueue = new LinkedList<ChunkPacket>();

	public void tick() {
		if (!spawned) {
			ChunkPacket chunkpacket = chunkQueue.poll();
			if (chunkpacket != null) {
				try {
					loadedChunkCount++;
					networkManager.sendPEPacket(chunkpacket);
				} catch (Exception e) {
					networkManager.close(new ChatComponentText(e.getMessage()));
				}
			}
		}
	}

	private final LocalStorage storage = new LocalStorage();

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public List<? extends ClientboundPEPacket> transform(Packet packet) throws Exception {
		PacketDataSerializer packetdata = new PacketDataSerializer(Allocator.allocateBuffer(), ProtocolVersion.MINECRAFT_1_8);
		try {
			int id = EnumProtocol.PLAY.a(EnumProtocolDirection.CLIENTBOUND, packet);
			packet.b(packetdata);
			switch (id) {
				case 0x00: { //PacketPlayOutKeepAlive
					return Collections.singletonList(new PongPacket(packetdata.readVarInt()));
				}
				case 0x01: { //PacketPlayOutLogin
					//use this packet to sent needed login packets
					Player bukkitplayer = getPlayer(networkManager).getBukkitEntity();
					return Arrays.asList(
						new ClientboundPEPacket[] {
							new StartGamePacket(
								bukkitplayer.getGameMode().getValue() & 0x1,
								bukkitplayer.getEntityId(),
								bukkitplayer.getWorld().getSpawnLocation(),
								bukkitplayer.getLocation()
							),
							new SetTimePacket()
						}
					);
				}
				case 0x02: { //PacketPlayOutChat
					return Collections.singletonList(new ChatPacket(Utils.fromComponent(packetdata.d())));
				}
				case 0x06: { //PacketPlayOutUpdateHealth
					return Collections.singletonList(new SetHealthPacket((int) packetdata.readFloat()));
				}
				case 0x08: { //PacketlayOutPosition
					Player player = getPlayer(networkManager).getBukkitEntity();
					double x = packetdata.readDouble();
					double y = packetdata.readDouble();
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
					return Collections.singletonList(new MovePacket(
						player.getEntityId(),
						(float) x, (float) y, (float) z,
						yaw, pitch, false)
					);
				}
				case 0x21: { //PacketPlayOutMapChunk
					int x = packetdata.readInt();
					int z = packetdata.readInt();
					boolean cont = packetdata.readBoolean();
					int mask = packetdata.readShort();
					Chunk chunk = getPlayer(networkManager).getWorld().getChunkIfLoaded(x, z);
					if (chunk != null && !(cont && mask == 0)) {
						loadedChunkCount++;
						return Collections.singletonList(new ChunkPacket(chunk));
					} else {
						return Collections.emptyList();
					}
				}
				case 0x22: { //PacketPlayOutMultiBlockChange
					int chunkX = packetdata.readInt();
					int chunkZ = packetdata.readInt();
					int count = packetdata.e();
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
				case 0x23: { //PacketPlayOutBlockChange
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
				case 0x26: { //PacketPlayOutMapChunkBulk
					packetdata.readBoolean();
					World world = getPlayer(networkManager).getWorld();
					ArrayList<ChunkPacket> packets = new ArrayList<ChunkPacket>();
					int count = packetdata.readVarInt();
					for (int i = 0; i < count; i++) {
						int x = packetdata.readInt();
						int z = packetdata.readInt();
						packetdata.readShort();
						Chunk chunk = world.getChunkIfLoaded(x, z);
						if (chunk != null) {
							packets.add(new ChunkPacket(chunk));
						}
					}
					//MCPE is kinda retarded and can't accept many chunks at once, so we will have to delay them if player is not logged in
					if (spawned) {
						return packets;
					} else {
						chunkQueue.addAll(packets);
						return Collections.emptyList();
					}
				}
				case 0x38: { // PacketPlayOutPlayerInfo
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
								break;
							}
							case 4: {
								String playerName = storage.getPlayerListName(uuid);
								if (playerName != null) {
									storage.removePlayerListName(uuid);
								}
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
							default: {
								break;
							}
						}
					}
					break;
				}
			}
		} finally {
			packetdata.release();
		}
		return Collections.emptyList();
	}

	private EntityPlayer getPlayer(NetworkManager networkManager) {
		return ((PlayerConnection) networkManager.getPacketListener()).player;
	}

}
