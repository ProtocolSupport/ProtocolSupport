package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__17r2;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__18.ChunkLight;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__17r2.AbstractMaskChunkData;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.chunk.ChunkBiomeLegacyWriter;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterPaletted;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;

public class ChunkData extends AbstractMaskChunkData implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public ChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeTransformer.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void write() {
		io.writeClientbound(ChunkLight.create(coord, false, setSkyLightMask, setBlockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight));

		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		ArrayCodec.writeVarIntLongArray(chunkdataPacket, mask.toLongArray());
		ItemStackCodec.writeDirectTag(chunkdataPacket, heightmaps);
		MiscDataCodec.writeVarIntCountPrefixedType(chunkdataPacket, this, (biomesTo, chunkdataInstance) -> ChunkBiomeLegacyWriter.writeBiomes(
			biomesTo, chunkdataInstance.clientCache, chunkdataInstance.biomeLegacyDataTable, chunkdataInstance.sections
		));
		MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (sectionsTo, chunkdataInstance) -> ChunkBlockdataLegacyWriterPaletted.writeSectionsBlockdataPadded(
			sectionsTo,
			15, chunkdataInstance.blockLegacyDataTable, chunkdataInstance.flatteningBlockDataTable,
			chunkdataInstance.sections, chunkdataInstance.mask
		));
		ArrayCodec.writeVarIntTArray(
			chunkdataPacket,
			tiles,
			(tileTo, tile) -> ItemStackCodec.writeDirectTag(tileTo, tileRemapper.remap(tile).getNBT())
		);
		io.writeClientbound(chunkdataPacket);
	}

}
