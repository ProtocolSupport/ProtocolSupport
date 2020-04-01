package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.util.Map;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunk;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterBeta;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterBeta.ChunkUpdateData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.utils.netty.Compressor;

public class Chunk extends AbstractChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	@Override
	public void writeToClient() {
		String locale = cache.getAttributesCache().getLocale();
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();

		if (full) {
			codec.write(createPreChunk(coord, true));
		}
		for (ChunkUpdateData udata : ChunkWriterBeta.serializeChunk(
			full, blockMask,
			blockDataRemappingTable,
			cachedChunk, hasSkyLight,
			sectionNumber -> {}
		)) {
			ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
			chunkdata.writeInt(coord.getX() << 4);
			chunkdata.writeShort(udata.getSectionStart() << 4);
			chunkdata.writeInt(coord.getZ() << 4);
			chunkdata.writeByte(15); //size x
			chunkdata.writeByte((udata.getSectionCount() << 4) - 1);
			chunkdata.writeByte(15); //size z
			byte[] compressed = Compressor.compressStatic(udata.getData());
			chunkdata.writeInt(compressed.length);
			chunkdata.writeBytes(compressed);
			codec.write(chunkdata);
		}

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				if (tile.getType() == TileEntityType.SIGN) {
					codec.write(BlockTileUpdate.createSignUpdate(version, locale, tile));
				}
			}
		}
	}

	public static ClientBoundPacketData createPreChunk(ChunkCoord chunk, boolean load) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD);
		PositionSerializer.writeIntChunkCoord(serializer, chunk);
		serializer.writeBoolean(load);
		return serializer;
	}

}
