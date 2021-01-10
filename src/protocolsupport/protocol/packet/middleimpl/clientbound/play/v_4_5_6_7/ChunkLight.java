package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.Map;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterByte;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.BitUtils;
import protocolsupport.utils.netty.Compressor;

public class ChunkLight extends AbstractChunkCacheChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void write() {
		String locale = clientCache.getLocale();
		boolean hasSkyLight = clientCache.hasDimensionSkyLight();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(false); //full
		chunkdata.writeShort(blockMask);
		chunkdata.writeShort(0);
		byte[] compressed = Compressor.compressStatic(ChunkWriterByte.serializeSectionsAndBiomes(
			blockMask,
			cachedChunk, blockDataRemappingTable, hasSkyLight,
			null, null, null
		));
		chunkdata.writeInt(compressed.length);
		chunkdata.writeBytes(compressed);
		codec.writeClientbound(chunkdata);

		Map<Position, TileEntity>[] tiles = cachedChunk.getTiles();
		for (int sectionNumber = 0; sectionNumber < tiles.length; sectionNumber++) {
			if (BitUtils.isIBitSet(blockMask, sectionNumber)) {
				for (TileEntity tile : tiles[sectionNumber].values()) {
					codec.writeClientbound(BlockTileUpdate.create(version, locale, tile));
				}
			}
		}
	}

}
