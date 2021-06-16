package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;

public class ChunkLight extends AbstractChunkCacheChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable biomeLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(false); //full
		VarNumberSerializer.writeVarInt(chunkdataPacket, blockMask);
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
			ChunkWriterVariesWithLight.writeSectionsCompactPreFlattening(
				to, 13,
				chunksections.biomeLegacyDataTable,
				chunksections.cachedChunk, chunksections.blockMask, chunksections.clientCache.hasDimensionSkyLight()
			);
		});
		MiscSerializer.writeVarIntCountPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
			return ChunkWriterVariesWithLight.writeTiles(to, chunksections.cachedChunk, chunksections.blockMask);
		});
		codec.writeClientbound(chunkdataPacket);
	}

}
