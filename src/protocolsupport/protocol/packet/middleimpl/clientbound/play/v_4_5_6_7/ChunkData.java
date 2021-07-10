package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Map;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriteUtils;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterByte;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.netty.RecyclableWrapCompressor;

public class ChunkData extends AbstractChunkCacheChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		String locale = clientCache.getLocale();
		boolean hasSkyLight = clientCache.hasDimensionSkyLight();

		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_SINGLE);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(full);
		if (mask.isEmpty() && full) {
			chunkdataPacket.writeShort(1);
			chunkdataPacket.writeShort(0);
			byte[] compressed = ChunkWriteUtils.getEmptySectionByte(hasSkyLight);
			chunkdataPacket.writeInt(compressed.length);
			chunkdataPacket.writeBytes(compressed);
		} else {
			chunkdataPacket.writeShort(CollectionsUtils.getBitSetFirstLong(mask));
			chunkdataPacket.writeShort(0);
			byte[] compressed = RecyclableWrapCompressor.compressStatic(ChunkWriterByte.serializeSectionsAndBiomes(
				biomeLegacyDataTable, blockLegacyDataTable,
				clientCache, full ? biomes : null,
				cachedChunk, mask, hasSkyLight
			));
			chunkdataPacket.writeInt(compressed.length);
			chunkdataPacket.writeBytes(compressed);
		}
		codec.writeClientbound(chunkdataPacket);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				codec.writeClientbound(BlockTileUpdate.create(version, locale, tile));
			}
		}
	}

}
