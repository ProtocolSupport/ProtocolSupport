package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import java.util.Map;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterByte;
import protocolsupport.protocol.typeremapper.chunk.ChunkLegacyWriteUtils;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.netty.RecyclableWrapCompressor;

public class ChunkData extends AbstractChunkCacheChunkData implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public ChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeTransformer.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		String locale = clientCache.getLocale();
		boolean hasSkyLight = clientCache.hasDimensionSkyLight();

		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(full);
		if (mask.isEmpty() && full) {
			chunkdataPacket.writeShort(1);
			chunkdataPacket.writeShort(0);
			byte[] compressed = ChunkLegacyWriteUtils.getEmptySectionByte(hasSkyLight);
			chunkdataPacket.writeInt(compressed.length);
			chunkdataPacket.writeBytes(compressed);
		} else {
			chunkdataPacket.writeShort(CollectionsUtils.getBitSetFirstLong(mask));
			chunkdataPacket.writeShort(0);
			byte[] compressed = RecyclableWrapCompressor.compressStatic(ChunkBlockdataLegacyWriterByte.serializeSectionsAndBiomes(
				biomeLegacyDataTable, blockLegacyDataTable,
				clientCache, full ? sections[0].getBiomes() : null,
				cachedChunk, mask, hasSkyLight
			));
			chunkdataPacket.writeInt(compressed.length);
			chunkdataPacket.writeBytes(compressed);
		}
		io.writeClientbound(chunkdataPacket);

		for (Map<Position, TileEntity> sectionTiles : cachedChunk.getTiles()) {
			for (TileEntity tile : sectionTiles.values()) {
				io.writeClientbound(BlockTileUpdate.create(version, locale, tile));
			}
		}
	}

}
