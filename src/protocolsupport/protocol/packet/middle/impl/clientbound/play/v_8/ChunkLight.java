package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import java.util.Map;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__13.BlockTileUpdate;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterShort;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.CollectionsUtils;

public class ChunkLight extends AbstractChunkCacheChunkLight implements IClientboundMiddlePacketV8 {

	public ChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(false); //full
		chunkdataPacket.writeShort(CollectionsUtils.getBitSetFirstLong(blockMask));
		ArrayCodec.writeVarIntByteArray(chunkdataPacket, ChunkBlockdataLegacyWriterShort.serializeSections(
			blockLegacyDataTable,
			cachedChunk, blockMask, clientCache.hasDimensionSkyLight()
		));
		io.writeClientbound(chunkdataPacket);

		Map<Position, TileEntity>[] sectionTiles = cachedChunk.getTiles();
		for (int sectionIndex = 0; sectionIndex < sectionTiles.length; sectionIndex++) {
			if (blockMask.get(sectionIndex)) {
				for (TileEntity tile : sectionTiles[sectionIndex].values()) {
					io.writeClientbound(BlockTileUpdate.create(version, tile));
				}
			}
		}
	}

}
