package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Map;

import org.bukkit.block.Biome;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterByte;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterUtils;
import protocolsupport.protocol.typeremapper.utils.MappingTable.EnumMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.netty.Compressor;

public class ChunkData extends AbstractChunkCacheChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final EnumMappingTable<Biome> biomeRemappingTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IdMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void write() {
		String locale = clientCache.getLocale();
		boolean hasSkyLight = clientCache.hasDimensionSkyLight();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(full);
		if ((blockMask == 0) && full) {
			chunkdata.writeShort(1);
			chunkdata.writeShort(0);
			byte[] compressed = ChunkWriterUtils.getEmptySectionByte(hasSkyLight);
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		} else {
			chunkdata.writeShort(blockMask);
			chunkdata.writeShort(0);
			byte[] compressed = Compressor.compressStatic(ChunkWriterByte.serializeSectionsAndBiomes(
				blockMask,
				cachedChunk, blockDataRemappingTable, hasSkyLight,
				full ? biomes : null, clientCache, biomeRemappingTable
			));
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		}
		codec.writeClientbound(chunkdata);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				codec.writeClientbound(BlockTileUpdate.create(version, locale, tile));
			}
		}
	}

}
