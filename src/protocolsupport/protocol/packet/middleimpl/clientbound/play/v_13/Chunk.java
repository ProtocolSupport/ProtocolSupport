package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import java.util.Arrays;
import java.util.stream.Collectors;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunknew.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.chunk.ChunkConstants;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chunk extends MiddleChunk {

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		PositionSerializer.writeIntChunkCoord(serializer, coord);
		serializer.writeBoolean(full);
		VarNumberSerializer.writeVarInt(serializer, blockMask);
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
		ArraySerializer.writeVarIntByteArray(serializer, to -> {
			for (int i = 0; i < ChunkConstants.SECTION_COUNT_BLOCKS; i++) {
				if (Utils.isBitSet(blockMask, i)) {
					ChunkWriterVariesWithLight.writeSectionData(to, blockDataRemappingTable, flatteningBlockDataTable, cachedChunk, hasSkyLight, i);
				}
			}
			if (full) {
				for (int i = 0; i < biomeData.length; i++) {
					to.writeInt(biomeData[i]);
				}
			}
		});
		ArraySerializer.writeVarIntTArray(
			serializer,
			Arrays.stream(cachedChunk.getTiles()).flatMap(l -> l.values().stream()).collect(Collectors.toList()),
			(to, tile) -> ItemStackSerializer.writeTag(to, version, tile.getNBT())
		);

		return RecyclableSingletonList.create(serializer);
	}

}
