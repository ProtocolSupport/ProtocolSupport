package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBB;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerVariesLegacy;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	protected final ChunkTransformerBB transformer = new ChunkTransformerVariesLegacy(LegacyBlockData.REGISTRY.getTable(version), TileEntityRemapper.getRemapper(version), cache.getTileCache());

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		transformer.loadData(chunk, data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full, tiles);

		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		PositionSerializer.writeChunkCoord(serializer, chunk);
		serializer.writeBoolean(full);
		VarNumberSerializer.writeVarInt(serializer, bitmask);
		ArraySerializer.writeVarIntByteArray(serializer, transformer::writeLegacyData);
		ArraySerializer.writeVarIntTArray(
			serializer,
			transformer.remapAndGetTiles(),
			(to, tile) -> ItemStackSerializer.writeTag(to, version, tile.getNBT())
		);

		return RecyclableSingletonList.create(serializer);
	}

}
