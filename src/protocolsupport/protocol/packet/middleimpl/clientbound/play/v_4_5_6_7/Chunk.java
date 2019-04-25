package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

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

//	protected final ChunkTransformerBA transformer = new ChunkTransformerByte(LegacyBlockData.REGISTRY.getTable(version), TileEntityRemapper.getRemapper(version), cache.getTileCache());
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
//			byte[] compressed = EmptyChunk.getPre18ChunkData(hasSkyLight);
//			chunkdata.writeShort(1);
//			chunkdata.writeShort(0);
//			chunkdata.writeInt(compressed.length);
//			chunkdata.writeBytes(compressed);
//		} else {
//			byte[] compressed = Compressor.compressStatic(transformer.toLegacyData());
//			chunkdata.writeShort(bitmask);
//			chunkdata.writeShort(0);
//			chunkdata.writeInt(compressed.length);
//			chunkdata.writeBytes(compressed);
//		}
//		packets.add(chunkdata);
//
//		for (TileEntity tile : transformer.remapAndGetTiles()) {
//			packets.add(BlockTileUpdate.create(version, cache.getAttributesCache().getLocale(), tile));
//		}
//
//		return packets;
//	}

}
