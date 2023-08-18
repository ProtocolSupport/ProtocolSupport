package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1__14r2;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1__15.ChunkLight;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractLimitedHeightChunkData;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.chunk.ChunkBiomeLegacyWriter;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterPaletted;
import protocolsupport.protocol.typeremapper.chunk.ChunkHeightMapLegacyWriter;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.utils.CollectionsUtils;

public class ChunkData extends AbstractLimitedHeightChunkData implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2 {

	public ChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeTransformer.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void write() {
		io.writeClientbound(ChunkLight.create(coord, setSkyLightMask, setBlockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight));

		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(true); //full
		VarNumberCodec.writeVarInt(chunkdataPacket, CollectionsUtils.getBitSetFirstLong(mask));
		ItemStackCodec.writeDirectTag(chunkdataPacket, ChunkHeightMapLegacyWriter.transform(heightmaps));
		MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunkdataInstance) -> {
			ChunkBlockdataLegacyWriterPaletted.writeSectionsBlockdataCompact(
				to, 14,
				chunkdataInstance.blockLegacyDataTable, chunkdataInstance.flatteningBlockDataTable,
				chunkdataInstance.sections, chunkdataInstance.mask
			);
			for (int biomeId : ChunkBiomeLegacyWriter.toPerBlockBiomeData(chunkdataInstance.clientCache, chunkdataInstance.biomeLegacyDataTable, chunkdataInstance.sections[0].getBiomes())) {
				to.writeInt(biomeId);
			}
		});
		ArrayCodec.writeVarIntTArray(
			chunkdataPacket,
			tiles,
			(to, tile) -> ItemStackCodec.writeDirectTag(to, tileRemapper.remap(tile).getNBT())
		);
		io.writeClientbound(chunkdataPacket);
	}

}
