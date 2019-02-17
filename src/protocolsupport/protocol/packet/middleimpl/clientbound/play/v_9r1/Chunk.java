package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBB;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerVariesLegacy;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	protected final ChunkTransformerBB transformer = new ChunkTransformerVariesLegacy(LegacyBlockData.REGISTRY.getTable(version), TileEntityRemapper.getRemapper(version), cache.getTileCache());

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		transformer.loadData(chunk, data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full, tiles);

		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		PositionSerializer.writeChunkCoord(chunkdata, chunk);
		chunkdata.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunkdata, bitmask);
		ArraySerializer.writeVarIntByteArray(chunkdata, transformer::writeLegacyData);
		packets.add(chunkdata);

		for (TileEntity tile : transformer.remapAndGetTiles()) {
			packets.add(BlockTileUpdate.create(version, tile));
		}

		return packets;
	}

}
