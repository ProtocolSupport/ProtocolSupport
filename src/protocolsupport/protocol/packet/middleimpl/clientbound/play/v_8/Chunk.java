package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.basic.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBA;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerShort;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	protected final ChunkTransformerBA transformer = new ChunkTransformerShort(LegacyBlockData.REGISTRY.getTable(connection.getVersion()), cache.getTileCache());

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		PositionSerializer.writeChunkCoord(chunkdata, chunk);
		chunkdata.writeBoolean(full);
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
		if ((bitmask == 0) && full) {
			chunkdata.writeShort(1);
			ArraySerializer.writeVarIntByteArray(chunkdata, EmptyChunk.get18ChunkData(hasSkyLight));
		} else {
			transformer.loadData(chunk, data, bitmask, hasSkyLight, full, tiles);
			chunkdata.writeShort(bitmask);
			ArraySerializer.writeVarIntByteArray(chunkdata, transformer.toLegacyData());
			for (NBTCompound tile : transformer.remapAndGetTiles()) {
				packets.add(BlockTileUpdate.createPacketData(
					connection,
					TileEntityType.getByRegistryId(TileNBTRemapper.getTileType(tile)),
					TileNBTRemapper.getPosition(tile),
					tile
				));
			}
		}
		packets.add(chunkdata);
		return packets;
	}

}
