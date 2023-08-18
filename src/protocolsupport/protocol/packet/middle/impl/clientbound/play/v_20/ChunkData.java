package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChunkData;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.chunk.ChunkSectionWriter;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.Position;

public class ChunkData extends MiddleChunkData implements
IClientboundMiddlePacketV20 {

	public ChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeTransformer.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		ItemStackCodec.writeDirectTag(chunkdataPacket, heightmaps);
		MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (sectionsTo, chunkdataInstance) -> ChunkSectionWriter.writeSectionsBlockdataBiomes(
			sectionsTo,
			15, chunkdataInstance.blockLegacyDataTable, chunkdataInstance.flatteningBlockDataTable,
			chunkdataInstance.clientCache, chunkdataInstance.biomeLegacyDataTable,
			chunkdataInstance.sections
		));
		ArrayCodec.writeVarIntTArray(chunkdataPacket, tiles, (tileTo, tile) -> {
			tile = tileRemapper.remap(tile);
			Position position = tile.getPosition();

			tileTo.writeByte(((position.getX() & 0xF) << 4) | (position.getZ() & 0xF));
			tileTo.writeShort(position.getY());
			VarNumberCodec.writeVarInt(tileTo, tile.getType().getNetworkId());
			ItemStackCodec.writeDirectTag(tileTo, tile.getNBT());
		});
		ChunkLight.writeData(chunkdataPacket, setSkyLightMask, setBlockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight);
		io.writeClientbound(chunkdataPacket);
	}

}
