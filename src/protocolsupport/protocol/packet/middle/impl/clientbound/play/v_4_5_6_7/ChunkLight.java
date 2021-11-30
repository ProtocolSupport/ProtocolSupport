package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7;

import java.util.Map;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterByte;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.netty.RecyclableWrapCompressor;

public class ChunkLight extends AbstractChunkCacheChunkLight implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public ChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		String locale = clientCache.getLocale();
		boolean hasSkyLight = clientCache.hasDimensionSkyLight();

		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(false); //full
		chunkdataPacket.writeShort(CollectionsUtils.getBitSetFirstLong(blockMask));
		chunkdataPacket.writeShort(0); //mask high bits
		byte[] compressed = RecyclableWrapCompressor.compressStatic(ChunkBlockdataLegacyWriterByte.serializeSectionsAndBiomes(
			null, blockLegacyDataTable,
			null, null,
			cachedChunk, blockMask, hasSkyLight
		));
		chunkdataPacket.writeInt(compressed.length);
		chunkdataPacket.writeBytes(compressed);
		io.writeClientbound(chunkdataPacket);

		Map<Position, TileEntity>[] sectionTiles = cachedChunk.getTiles();
		for (int sectionIndex = 0; sectionIndex < sectionTiles.length; sectionIndex++) {
			if (blockMask.get(sectionIndex)) {
				for (TileEntity tile : sectionTiles[sectionIndex].values()) {
					io.writeClientbound(BlockTileUpdate.create(version, locale, tile));
				}
			}
		}
	}

}
