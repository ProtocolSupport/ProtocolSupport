package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import java.util.Map;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.BitUtils;

public class ChunkLight extends AbstractChunkCacheChunkLight {

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(false); //full
		VarNumberSerializer.writeVarInt(chunkdataPacket, blockMask);
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
			ChunkWriterVariesWithLight.writeSectionsCompactPreFlattening(
				to,
				13,
				chunksections.blockLegacyDataTable,
				chunksections.cachedChunk, chunksections.blockMask, chunksections.clientCache.hasDimensionSkyLight()
			);
		});
		codec.writeClientbound(chunkdataPacket);

		Map<Position, TileEntity>[] tiles = cachedChunk.getTiles();
		for (int sectionNumber = 0; sectionNumber < tiles.length; sectionNumber++) {
			if (BitUtils.isIBitSet(blockMask, sectionNumber)) {
				for (TileEntity tile : tiles[sectionNumber].values()) {
					codec.writeClientbound(BlockTileUpdate.create(version, tile));
				}
			}
		}
	}

}
