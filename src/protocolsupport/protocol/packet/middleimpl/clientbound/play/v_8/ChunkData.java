package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import java.util.Map;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterShort;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterUtils;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;

public class ChunkData extends AbstractChunkCacheChunkData {

	public ChunkData(ConnectionImpl connection) {
		super(connection);
	}

	protected final IdMappingTable biomeRemappingTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IdMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		boolean hasSkyLight = cache.getClientCache().hasSkyLightInCurrentDimension();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(full);
		if ((blockMask == 0) && full) {
			chunkdata.writeShort(1);
			ArraySerializer.writeVarIntByteArray(chunkdata, ChunkWriterUtils.getEmptySectionShort(hasSkyLight));
		} else {
			chunkdata.writeShort(blockMask);
			ArraySerializer.writeVarIntByteArray(chunkdata, to -> {
				to.writeBytes(ChunkWriterShort.serializeSections(blockMask, blockDataRemappingTable, cachedChunk, hasSkyLight, sectionNumber -> {}));
				if (full) {
					int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(biomes);
					for (int biomeId : legacyBiomeData) {
						to.writeByte(biomeRemappingTable.get(biomeId));
					}
				}
			});
		}
		codec.write(chunkdata);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				codec.write(BlockTileUpdate.create(version, tile));
			}
		}
	}

}
