package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkData;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVaries;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;

public class ChunkData extends MiddleChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdataPacket, coord);
		ArraySerializer.writeVarIntLongArray(chunkdataPacket, blockMask.toLongArray());
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
				chunksections.sections, chunksections.blockMask
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
