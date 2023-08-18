package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r2__12r2;

import org.bukkit.NamespacedKey;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChunkData;
import protocolsupport.protocol.typeremapper.basic.BiomeTransformer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkBiomeLegacyWriter;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterPalettedWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.GenericMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IntMappingTable;
import protocolsupport.utils.CollectionsUtils;

public class ChunkData extends AbstractChunkCacheChunkData implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public ChunkData(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericMappingTable<NamespacedKey> biomeLegacyDataTable = BiomeTransformer.REGISTRY.getTable(version);
	protected final IntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(full);
		VarNumberCodec.writeVarInt(chunkdataPacket, CollectionsUtils.getBitSetFirstLong(mask));
		MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunkdataInstance) -> {
			ChunkBlockdataLegacyWriterPalettedWithLight.writeSectionsBlockdataLightCompactPreFlattening(
				to,
				13, chunkdataInstance.blockLegacyDataTable,
				chunkdataInstance.cachedChunk, chunkdataInstance.mask, chunkdataInstance.clientCache.hasDimensionSkyLight()
			);
			if (chunkdataInstance.full) {
				for (int biomeId : ChunkBiomeLegacyWriter.toPerBlockBiomeData(chunkdataInstance.clientCache, chunkdataInstance.biomeLegacyDataTable, chunkdataInstance.sections[0].getBiomes())) {
					to.writeInt(biomeId);
				}
			}
		});
		MiscDataCodec.writeVarIntCountPrefixedType(chunkdataPacket, this, (to, chunkdataInstance) -> ChunkBlockdataLegacyWriterPalettedWithLight.writeTiles(
			to, chunkdataInstance.cachedChunk, chunkdataInstance.mask
		));
		io.writeClientbound(chunkdataPacket);
	}

}
