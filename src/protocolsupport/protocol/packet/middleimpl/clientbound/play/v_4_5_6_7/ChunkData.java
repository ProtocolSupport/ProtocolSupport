package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Map;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterByte;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterUtils;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.netty.Compressor;

public class ChunkData extends AbstractChunkCacheChunkData {

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public ChunkData(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		String locale = cache.getClientCache().getLocale();
		boolean hasSkyLight = cache.getClientCache().hasSkyLightInCurrentDimension();

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
				blockMask, blockDataRemappingTable, cachedChunk, hasSkyLight, full ? biomes : null, sectionNumber -> {}
			));
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		}
		codec.write(chunkdata);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				codec.write(BlockTileUpdate.create(version, locale, tile));
			}
		}
	}

}
