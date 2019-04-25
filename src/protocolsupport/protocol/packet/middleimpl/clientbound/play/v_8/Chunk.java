package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

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

//	protected final ChunkTransformerBA transformer = new ChunkTransformerShort(LegacyBlockData.REGISTRY.getTable(version), TileEntityRemapper.getRemapper(version), cache.getTileCache());
//
//	@Override
//	public RecyclableCollection<ClientBoundPacketData> toData() {
//		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
//		transformer.loadData(coord, data, bitmask, hasSkyLight, full, tiles);
//
//		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
//
//		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
//		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
//		chunkdata.writeBoolean(full);
//		if ((bitmask == 0) && full) {
//			chunkdata.writeShort(1);
//			ArraySerializer.writeVarIntByteArray(chunkdata, EmptyChunk.get18ChunkData(hasSkyLight));
//		} else {
//			chunkdata.writeShort(bitmask);
//			ArraySerializer.writeVarIntByteArray(chunkdata, transformer.toLegacyData());
//		}
//		packets.add(chunkdata);
//
//		for (TileEntity tile : transformer.remapAndGetTiles()) {
//			packets.add(BlockTileUpdate.create(version, tile));
//		}
//
//		return packets;
//	}

}
