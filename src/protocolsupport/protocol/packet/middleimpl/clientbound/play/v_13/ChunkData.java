package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import java.util.Map;

import org.bukkit.block.Biome;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.EnumMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;

public class ChunkData extends AbstractChunkCacheChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final EnumMappingTable<Biome> biomeRemappingTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IdMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
			ChunkWriterVariesWithLight.writeSectionsCompactFlattening(
				to, chunksections.blockMask, 14,
				chunksections.blockDataRemappingTable, chunksections.flatteningBlockDataTable,
				chunksections.cachedChunk, chunksections.clientCache.hasDimensionSkyLight(),
				sectionNumber -> {}
			);
			if (chunksections.full) {
				int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(chunksections.biomes);
				for (int biomeId : legacyBiomeData) {
					to.writeInt(BiomeRemapper.mapBiome(biomeId, chunksections.clientCache, chunksections.biomeRemappingTable));
				}
			}
		});
		MiscSerializer.writeVarIntCountPrefixedType(chunkdata, cachedChunk.getTiles(), (tilesTo, chunkTiles) -> {
			int count = 0;
			for (Map<Position, TileEntity> sectionTiles : chunkTiles) {
				for (TileEntity tile : sectionTiles.values()) {
					ItemStackSerializer.writeDirectTag(tilesTo, tile.getNBT());
				}
				count += sectionTiles.size();
			}
			return count;
		});
		codec.write(chunkdata);
	}

}
