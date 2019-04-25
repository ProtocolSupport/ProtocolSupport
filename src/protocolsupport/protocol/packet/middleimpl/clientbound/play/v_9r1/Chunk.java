package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

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
//		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
//
//		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
//		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
//		chunkdata.writeBoolean(full);
//		VarNumberSerializer.writeVarInt(chunkdata, bitmask);
//		ArraySerializer.writeVarIntByteArray(chunkdata, transformer::writeLegacyData);
//		packets.add(chunkdata);
//
//		for (TileEntity tile : transformer.remapAndGetTiles()) {
//			packets.add(BlockTileUpdate.create(version, tile));
//		}
//
//		return packets;
//	}

}
