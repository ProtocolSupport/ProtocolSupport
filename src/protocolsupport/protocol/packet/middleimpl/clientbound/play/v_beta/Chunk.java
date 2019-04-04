package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBeta;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBeta.ChunkUpdateData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk {

	protected final ChunkTransformerBeta transformer = new ChunkTransformerBeta(LegacyBlockData.REGISTRY.getTable(version), TileEntityRemapper.getRemapper(version), cache.getTileCache());

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
		transformer.loadData(chunk, data, bitmask, hasSkyLight, full, tiles);

		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();

		if (full) {
			packets.add(createPreChunk(chunk, true));
		}
		for (ChunkUpdateData udata : transformer.toLegacyData(full)) {
			ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
			chunkdata.writeInt(chunk.getX() << 4);
			chunkdata.writeShort(udata.getSectionStart() << 4);
			chunkdata.writeInt(chunk.getZ() << 4);
			chunkdata.writeByte(15); //size x
			chunkdata.writeByte((udata.getSectionCount() << 4) - 1);
			chunkdata.writeByte(15); //size z
			byte[] compressed = Compressor.compressStatic(udata.getData());
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
			packets.add(chunkdata);
		}

		return packets;
	}

	public static ClientBoundPacketData createPreChunk(ChunkCoord chunk, boolean load) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_UNLOAD_ID);
		PositionSerializer.writeChunkCoord(serializer, chunk);
		serializer.writeBoolean(load);
		return serializer;
	}

}
