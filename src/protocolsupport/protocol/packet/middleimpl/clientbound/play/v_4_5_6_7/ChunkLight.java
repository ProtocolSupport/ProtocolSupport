package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import java.util.ArrayList;
import java.util.List;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterByte;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.utils.netty.Compressor;

public class ChunkLight extends AbstractChunkCacheChunkLight {


	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	protected final List<ClientBoundPacketData> blocktileupdates = new ArrayList<>();

	@Override
	protected void writeToClient() {
		int blockMask = ((setSkyLightMask | setBlockLightMask | emptySkyLightMask | emptyBlockLightMask) >> 1) & 0xFFFF;
		String locale = cache.getClientCache().getLocale();
		boolean hasSkyLight = cache.getClientCache().hasDimensionSkyLight();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(false); //full
		chunkdata.writeShort(blockMask);
		chunkdata.writeShort(0);
		byte[] compressed = Compressor.compressStatic(ChunkWriterByte.serializeSectionsAndBiomes(
			blockMask,
			cachedChunk, blockDataRemappingTable, hasSkyLight,
			null, null, null,
			sectionNumber ->
				cachedChunk.getTiles(sectionNumber).values()
				.forEach(tile -> blocktileupdates.add(BlockTileUpdate.create(version, locale, tile)))
		));
		chunkdata.writeInt(compressed.length);
		chunkdata.writeBytes(compressed);

		codec.write(chunkdata);
		for (ClientBoundPacketData blocktileupdate : blocktileupdates) {
			codec.write(blocktileupdate);
		}
	}

	@Override
	protected void cleanup() {
		blocktileupdates.clear();
	}

}
