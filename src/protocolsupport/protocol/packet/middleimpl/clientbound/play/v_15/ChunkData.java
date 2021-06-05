package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.packet.PacketType;
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
import protocolsupport.protocol.typeremapper.chunk.ChunkHeightMapTransformer;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVaries;
import protocolsupport.protocol.typeremapper.legacy.LegacyBiomeData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;

public class ChunkData extends MiddleChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final GenericMappingTable<NamespacedKey> biomeRemappingTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IdMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		ItemStackSerializer.writeDirectTag(chunkdata, ChunkHeightMapTransformer.transform(heightmaps));
		if (full) {
			for (int biome : LegacyBiomeData.toLegacy1024EntryBiomeData(biomes)) {
				chunkdata.writeInt(BiomeRemapper.mapLegacyBiome(clientCache, biomeRemappingTable, biome));
			}
		}
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
			ChunkWriterVaries.writeSectionsCompact(
				to, chunksections.blockMask, 14,
				chunksections.blockDataRemappingTable,
				chunksections.flatteningBlockDataTable,
				chunksections.sections
			);
		});
		ArraySerializer.writeVarIntTArray(
			chunkdata,
			tiles,
			(to, tile) -> ItemStackSerializer.writeDirectTag(to, tileRemapper.remap(tile).getNBT())
		);
		codec.writeClientbound(chunkdata);
	}

}
