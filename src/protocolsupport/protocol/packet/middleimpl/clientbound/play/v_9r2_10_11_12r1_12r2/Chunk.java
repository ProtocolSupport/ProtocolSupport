package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return null;
	}

//	protected final ChunkTransformerBB transformer = new ChunkTransformerVariesLegacy(LegacyBlockData.REGISTRY.getTable(version), TileEntityRemapper.getRemapper(version), cache.getTileCache());
//
//	@Override
//	public RecyclableCollection<ClientBoundPacketData> toData() {
//		transformer.loadData(coord, data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full, tiles);
//
//		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
//		PositionSerializer.writeIntChunkCoord(serializer, coord);
//		serializer.writeBoolean(full);
//		VarNumberSerializer.writeVarInt(serializer, bitmask);
//		ArraySerializer.writeVarIntByteArray(serializer, transformer::writeLegacyData);
//		ArraySerializer.writeVarIntTArray(
//			serializer,
//			transformer.remapAndGetTiles(),
//			(to, tile) -> ItemStackSerializer.writeTag(to, version, tile.getNBT())
//		);
//
//		return RecyclableSingletonList.create(serializer);
//	}

}
