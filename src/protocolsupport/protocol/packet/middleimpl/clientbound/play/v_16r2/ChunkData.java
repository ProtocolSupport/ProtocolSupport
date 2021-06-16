package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r2;

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
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(true); //full
		VarNumberSerializer.writeVarInt(chunkdataPacket, limitedBlockMask);
		ItemStackSerializer.writeDirectTag(chunkdataPacket, heightmaps);
		VarNumberSerializer.writeVarInt(chunkdataPacket, biomes.length);
		for (int biome : biomes) {
			VarNumberSerializer.writeVarInt(chunkdataPacket, BiomeRemapper.mapCustomBiome(clientCache, biomeLegacyDataTable, biome));
		}
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
			ChunkWriterVaries.writeSectionsPadded(
				to,
				15,
				chunksections.blockLegacyDataTable, chunksections.flatteningBlockDataTable,
				chunksections.sections, chunksections.limitedBlockMask, chunksections.limitedHeightOffset
			);
		});
		ArraySerializer.writeVarIntTArray(
			chunkdataPacket,
			tiles,
			(tileTo, tile) -> ItemStackSerializer.writeDirectTag(tileTo, tileRemapper.remap(tile).getNBT())
		);
		codec.writeClientbound(chunkdataPacket);
	}

}
