package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class ChunkLight extends AbstractChunkCacheChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(false); //full
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
			ChunkWriterVariesWithLight.writeSectionsCompactPreFlattening(
				to, chunksections.blockMask, 13,
				chunksections.blockDataRemappingTable,
				chunksections.cachedChunk,
				chunksections.clientCache.hasDimensionSkyLight()
			);
		});
		MiscSerializer.writeVarIntCountPrefixedType(chunkdata, this, (to, chunksections) -> {
			return ChunkWriterVariesWithLight.writeTiles(to, chunksections.blockMask, chunksections.cachedChunk);
		});
		codec.writeClientbound(chunkdata);
	}

}
