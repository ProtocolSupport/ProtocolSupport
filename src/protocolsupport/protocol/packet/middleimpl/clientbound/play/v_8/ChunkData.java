package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import java.util.Map;

import org.bukkit.block.Biome;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterShort;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterUtils;
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

	@Override
	protected void write() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(full);
		if ((blockMask == 0) && full) {
			chunkdata.writeShort(1);
			ArraySerializer.writeVarIntByteArray(chunkdata, ChunkWriterUtils.getEmptySectionShort(clientCache.hasDimensionSkyLight()));
		} else {
			chunkdata.writeShort(blockMask);
			MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
				to.writeBytes(ChunkWriterShort.serializeSections(
					chunksections.blockMask,
					chunksections.blockDataRemappingTable,
					chunksections.cachedChunk,
					chunksections.clientCache.hasDimensionSkyLight()
				));
				if (chunksections.full) {
					int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(chunksections.biomes);
					for (int biomeId : legacyBiomeData) {
						to.writeByte(BiomeRemapper.mapBiome(biomeId, chunksections.clientCache, chunksections.biomeRemappingTable));
					}
				}
			});
		}
		codec.writeClientbound(chunkdata);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				codec.writeClientbound(BlockTileUpdate.create(version, tile));
			}
		}
	}

}
