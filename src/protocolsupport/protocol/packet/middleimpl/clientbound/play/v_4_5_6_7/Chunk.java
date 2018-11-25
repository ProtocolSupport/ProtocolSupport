package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBA;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerByte;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	protected final ChunkTransformerBA transformer = new ChunkTransformerByte(LegacyBlockData.REGISTRY.getTable(connection.getVersion()), TileNBTRemapper.getRemapper(connection.getVersion()), cache.getTileCache());

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		PositionSerializer.writeChunkCoord(chunkdata, chunk);
		chunkdata.writeBoolean(full);
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
		if ((bitmask == 0) && full) {
			byte[] compressed = EmptyChunk.getPre18ChunkData(hasSkyLight);
			chunkdata.writeShort(1);
			chunkdata.writeShort(0);
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		} else {
			transformer.loadData(chunk, data, bitmask, hasSkyLight, full, tiles);
			byte[] compressed = Compressor.compressStatic(transformer.toLegacyData());
			chunkdata.writeShort(bitmask);
			chunkdata.writeShort(0);
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
		}
		packets.add(chunkdata);
		for (TileEntity tile : transformer.remapAndGetTiles()) {
			packets.add(BlockTileUpdate.create(connection, tile));
		}
		return packets;
	}

}
