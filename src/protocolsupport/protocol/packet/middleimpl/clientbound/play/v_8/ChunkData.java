package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import java.util.Map;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriteUtils;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterShort;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.CollectionsUtils;

public class ChunkData extends AbstractChunkCacheChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_SINGLE);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(full);
		if (mask.isEmpty() && full) {
			chunkdataPacket.writeShort(1);
			ArrayCodec.writeVarIntByteArray(chunkdataPacket, ChunkWriteUtils.getEmptySectionShort(clientCache.hasDimensionSkyLight()));
		} else {
			chunkdataPacket.writeShort(CollectionsUtils.getBitSetFirstLong(mask));
			MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
				to.writeBytes(ChunkWriterShort.serializeSections(
					chunksections.blockLegacyDataTable,
					chunksections.cachedChunk, chunksections.mask, chunksections.clientCache.hasDimensionSkyLight()
				));
				if (chunksections.full) {
					int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(chunksections.biomes);
					for (int biomeId : legacyBiomeData) {
						to.writeByte(BiomeRemapper.mapLegacyBiome(chunksections.clientCache, chunksections.biomeLegacyDataTable, biomeId));
					}
				}
			});
		}
		codec.writeClientbound(chunkdataPacket);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				codec.writeClientbound(BlockTileUpdate.create(version, tile));
			}
		}
	}

}
