package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractLimitedHeightChunkData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVaries;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;

public class ChunkData extends AbstractLimitedHeightChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(true); //full
		chunkdata.writeBoolean(true); //use existing light
		VarNumberSerializer.writeVarInt(chunkdata, limitedBlockMask);
		ItemStackSerializer.writeDirectTag(chunkdata, heightmaps);
		for (int biome : LegacyBiomeData.toLegacy1024EntryBiomeData(biomes)) {
			chunkdata.writeInt(BiomeRemapper.mapCustomBiome(clientCache, biomeLegacyDataTable, biome));
		}
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
			ChunkWriterVaries.writeSectionsPadded(
				to,
				15,
				chunksections.blockLegacyDataTable, chunksections.flatteningBlockDataTable,
				chunksections.sections, chunksections.limitedBlockMask, chunksections.limitedHeightOffset
			);
		});
		ArraySerializer.writeVarIntTArray(
			chunkdata,
			tiles,
			(tileTo, tile) -> ItemStackSerializer.writeDirectTag(tileTo, tileRemapper.remap(tile).getNBT())
		);
		codec.writeClientbound(chunkdata);
	}

}
