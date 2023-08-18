package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r2__12r2;

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
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.chunk.ChunkBlockdataLegacyWriterPalettedWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.utils.CollectionsUtils;

public class ChunkLight extends AbstractChunkCacheChunkLight implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public ChunkLight(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntMappingTable biomeLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData chunkdataPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkdataPacket, coord);
		chunkdataPacket.writeBoolean(false); //full
		VarNumberCodec.writeVarInt(chunkdataPacket, CollectionsUtils.getBitSetFirstLong(blockMask));
		MiscDataCodec.writeVarIntLengthPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
			ChunkBlockdataLegacyWriterPalettedWithLight.writeSectionsBlockdataLightCompactPreFlattening(
				to, 13,
				chunksections.biomeLegacyDataTable,
				chunksections.cachedChunk, chunksections.blockMask, chunksections.clientCache.hasDimensionSkyLight()
			);
		});
		MiscDataCodec.writeVarIntCountPrefixedType(chunkdataPacket, this, (to, chunksections) -> {
			return ChunkBlockdataLegacyWriterPalettedWithLight.writeTiles(to, chunksections.cachedChunk, chunksections.blockMask);
		});
		io.writeClientbound(chunkdataPacket);
	}

}
