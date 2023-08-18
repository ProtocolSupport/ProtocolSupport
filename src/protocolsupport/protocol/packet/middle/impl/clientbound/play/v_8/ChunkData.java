package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import java.util.Map;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__13.BlockTileUpdate;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkBiomeLegacyWriter;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterShort;
import protocolsupport.protocol.typeremapper.chunk.ChunkLegacyWriteUtils;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.CollectionsUtils;

public class ChunkData extends AbstractChunkCacheChunkData implements IClientboundMiddlePacketV8 {

	public ChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeTransformer.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(full);
		if (mask.isEmpty() && full) {
			chunkdataPacket.writeShort(1);
			ArrayCodec.writeVarIntByteArray(chunkdataPacket, ChunkLegacyWriteUtils.getEmptySectionShort(clientCache.hasDimensionSkyLight()));
		} else {
			chunkdataPacket.writeShort(CollectionsUtils.getBitSetFirstLong(mask));
			MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunkdataInstance) -> {
				to.writeBytes(ChunkBlockdataLegacyWriterShort.serializeSections(
					chunkdataInstance.blockLegacyDataTable,
					chunkdataInstance.cachedChunk, chunkdataInstance.mask, chunkdataInstance.clientCache.hasDimensionSkyLight()
				));
				if (chunkdataInstance.full) {
					for (int biomeId : ChunkBiomeLegacyWriter.toPerBlockBiomeData(chunkdataInstance.clientCache, chunkdataInstance.biomeLegacyDataTable, chunkdataInstance.sections[0].getBiomes())) {
						to.writeByte(biomeId);
					}
				}
			});
		}
		io.writeClientbound(chunkdataPacket);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				io.writeClientbound(BlockTileUpdate.create(version, tile));
			}
		}
	}

}
