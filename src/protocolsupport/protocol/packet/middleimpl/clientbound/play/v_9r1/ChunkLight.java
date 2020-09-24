package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import java.util.Map;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.BitUtils;

public class ChunkLight extends AbstractChunkCacheChunkLight {

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeToClient() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(false); //full
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
			ChunkWriterVariesWithLight.writeSectionsCompactPreFlattening(
				to, chunksections.blockMask, 13,
				chunksections.blockDataRemappingTable,
				chunksections.cachedChunk,
				chunksections.clientCache.hasDimensionSkyLight()
			);
		});
		codec.write(chunkdata);

		Map<Position, TileEntity>[] tiles = cachedChunk.getTiles();
		for (int sectionNumber = 0; sectionNumber < tiles.length; sectionNumber++) {
			if (BitUtils.isIBitSet(blockMask, sectionNumber)) {
				for (TileEntity tile : tiles[sectionNumber].values()) {
					codec.write(BlockTileUpdate.create(version, tile));
				}
			}
		}
	}

}
