package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import java.util.Map;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.CollectionsUtils;

public class ChunkLight extends AbstractChunkCacheChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_SINGLE);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(false); //full
		VarNumberCodec.writeVarInt(chunkdataPacket, CollectionsUtils.getBitSetFirstLong(blockMask));
		MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
			ChunkWriterVariesWithLight.writeSectionsCompactPreFlattening(
				to,
				13,
				chunksections.blockLegacyDataTable,
				chunksections.cachedChunk, chunksections.blockMask, chunksections.clientCache.hasDimensionSkyLight()
			);
		});
		codec.writeClientbound(chunkdataPacket);

		Map<Position, TileEntity>[] sectionTiles = cachedChunk.getTiles();
		for (int sectionIndex = 0; sectionIndex < sectionTiles.length; sectionIndex++) {
			if (blockMask.get(sectionIndex)) {
				for (TileEntity tile : sectionTiles[sectionIndex].values()) {
					codec.writeClientbound(BlockTileUpdate.create(version, tile));
				}
			}
		}
	}

}
