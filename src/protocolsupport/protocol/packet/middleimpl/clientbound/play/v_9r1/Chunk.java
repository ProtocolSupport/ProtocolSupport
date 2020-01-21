package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import java.util.Map;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;

public class Chunk extends AbstractChunk {

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData chunksingle = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunksingle, coord);
		chunksingle.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunksingle, blockMask);
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
		ArraySerializer.writeVarIntByteArray(chunksingle, to -> {
			ChunkWriterVariesWithLight.writeSectionsPreFlattening(
				to, blockMask, 13,
				blockDataRemappingTable,
				cachedChunk, hasSkyLight,
				sectionNumber -> {}
			);
			if (full) {
				int[] legacyBiomeData = LegacyBiomeData.toLegacyBiomeData(biomes);
				for (int biomeId : legacyBiomeData) {
					to.writeByte(biomeId);
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
