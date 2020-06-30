package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r2_10_11_12r1_12r2;

import java.util.Map;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.IdRemappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;

public class ChunkData extends AbstractChunkCacheChunkData {

	public ChunkData(ConnectionImpl connection) {
		super(connection);
	}

	protected final IdRemappingTable biomeRemappingTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		boolean hasSkyLight = cache.getClientCache().hasSkyLightInCurrentDimension();
		ArraySerializer.writeVarIntByteArray(chunkdata, to -> {
			ChunkWriterVariesWithLight.writeSectionsCompactPreFlattening(
				to, blockMask, 13,
				blockDataRemappingTable,
				cachedChunk, hasSkyLight,
				sectionNumber -> {}
			);
			if (full) {
				int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(biomes);
				for (int biomeId : legacyBiomeData) {
					to.writeByte(biomeRemappingTable.getRemap(biomeId));
				}
			}
		});
		ArraySerializer.writeVarIntTArray(chunkdata, lTo -> {
			int count = 0;
			for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
				for (TileEntity tile : sectionTiles.values()) {
					ItemStackSerializer.writeDirectTag(lTo, tile.getNBT());
				}
				count += sectionTiles.size();
			}
			return count;
		});
		codec.write(chunkdata);
	}

}
