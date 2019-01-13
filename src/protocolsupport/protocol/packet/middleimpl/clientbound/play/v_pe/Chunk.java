package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.Bukkit;
import org.bukkit.util.NumberConversions;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.listeners.internal.ChunkUpdateRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.basic.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBB;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerPE;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	private final ChunkTransformerBB transformer = new ChunkTransformerPE(
		LegacyBlockData.REGISTRY.getTable(connection.getVersion()),
		TileEntityRemapper.getRemapper(connection.getVersion()),
		connection.getCache().getTileCache(),
		PEDataValues.BIOME.getTable(connection.getVersion())
	);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (full || (bitmask == 0xFFFF)) { //Only send full or 'full' chunks to PE.
			MovementCache movecache = cache.getMovementCache();
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			ProtocolVersion version = connection.getVersion();
			cache.getPEChunkMapCache().markSent(chunk);
			transformer.loadData(chunk, data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full, tiles);
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
			PositionSerializer.writePEChunkCoord(serializer, chunk);
			ArraySerializer.writeVarIntByteArray(serializer, chunkdata -> {
				transformer.writeLegacyData(chunkdata);
				chunkdata.writeByte(0); //borders
				for (TileEntity tile : transformer.remapAndGetTiles()) {
					ItemStackSerializer.writeTag(chunkdata, true, version, tile.getNBT());
				}
			});
			packets.add(serializer);
			ChunkCoord playerChunk = new ChunkCoord(NumberConversions.floor(movecache.getPEClientX()) >> 4, NumberConversions.floor(movecache.getPEClientZ()) >> 4);
			if (playerChunk.equals(chunk) && movecache.isFirstLocationSent()) {
				movecache.setClientImmobile(false);
				packets.add(EntityMetadata.updatePlayerMobility(connection));
			}
			if (!movecache.isFirstLocationSent()) {
				movecache.setFirstLocationSent(true);
				movecache.setPEClientPosition(chunk.getX() * 16, 100, chunk.getZ() * 16);
				packets.add(CustomPayload.create(version, InternalPluginMessageRequest.PEUnlockChannel));
			}
			if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_8)) {
				packets.add(createChunkPublisherUpdate((int) movecache.getPEClientX(), (int) movecache.getPEClientY(), (int) movecache.getPEClientZ()));
			}
			return packets;
		} else { //Request a full chunk.
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new ChunkUpdateRequest(chunk));
			return RecyclableEmptyList.get();
		}
	}

	public static ClientBoundPacketData createEmptyChunk(ChunkCoord chunk) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
		PositionSerializer.writePEChunkCoord(serializer, chunk);
		serializer.writeBytes(EmptyChunk.getPEChunkData());
		return serializer;
	}

	public static ClientBoundPacketData createChunkPublisherUpdate(int x, int y, int z) {
		ClientBoundPacketData networkChunkUpdate = ClientBoundPacketData.create(PEPacketIDs.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET);
		VarNumberSerializer.writeSVarInt(networkChunkUpdate, x);
		VarNumberSerializer.writeVarInt(networkChunkUpdate, y);
		VarNumberSerializer.writeSVarInt(networkChunkUpdate, z);
		//TODO: client view distance
		VarNumberSerializer.writeVarInt(networkChunkUpdate, Bukkit.getViewDistance() << 4);
		return networkChunkUpdate;
	}

}