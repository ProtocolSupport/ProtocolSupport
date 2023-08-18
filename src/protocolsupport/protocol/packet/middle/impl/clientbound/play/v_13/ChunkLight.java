package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterPalettedWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.utils.CollectionsUtils;

public class ChunkLight extends AbstractChunkCacheChunkLight implements IClientboundMiddlePacketV13 {

	public ChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(false); //full
		VarNumberCodec.writeVarInt(chunkdataPacket, CollectionsUtils.getBitSetFirstLong(blockMask));
		MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunkdataInstance) -> {
			ChunkBlockdataLegacyWriterPalettedWithLight.writeSectionsBlockdataLightCompactFlattening(
				to, 14,
				chunkdataInstance.blockLegacyDataTable, chunkdataInstance.flatteningBlockDataTable,
				chunkdataInstance.cachedChunk, chunkdataInstance.blockMask, chunkdataInstance.clientCache.hasDimensionSkyLight()
			);
		});
		MiscDataCodec.writeVarIntCountPrefixedType(chunkdataPacket, this, (to, chunksections) -> ChunkBlockdataLegacyWriterPalettedWithLight.writeTiles(
			to, chunksections.cachedChunk, chunksections.blockMask
		));
		io.writeClientbound(chunkdataPacket);
	}

}
