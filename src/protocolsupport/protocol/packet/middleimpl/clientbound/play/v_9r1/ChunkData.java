package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import java.util.Map;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;

public class ChunkData extends AbstractChunkCacheChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final IdMappingTable biomeRemappingTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IdMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		ClientBoundPacketData chunksingle = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunksingle, coord);
		chunksingle.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunksingle, blockMask);
		boolean hasSkyLight = cache.getClientCache().hasSkyLightInCurrentDimension();
		ArraySerializer.writeVarIntByteArray(chunksingle, to -> {
			ChunkWriterVariesWithLight.writeSectionsCompactPreFlattening(
				to, blockMask, 13,
				blockDataRemappingTable,
				cachedChunk, hasSkyLight,
				sectionNumber -> {}
			);
			if (full) {
				int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(biomes);
				for (int biomeId : legacyBiomeData) {
					to.writeByte(biomeRemappingTable.get(biomeId));
				}
			}
		});
		codec.write(chunksingle);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				codec.write(BlockTileUpdate.create(version, tile));
			}
		}
	}

}
