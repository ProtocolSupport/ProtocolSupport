package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheChunkLight;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.TileEntity;

public class ChunkLight extends AbstractChunkCacheChunkLight {

	public ChunkLight(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	@Override
	protected void writeToClient() {
		int blockMask = ((setSkyLightMask | setBlockLightMask | emptySkyLightMask | emptyBlockLightMask) >> 1) & 0xFFFF;
		List<Collection<TileEntity>> resendTiles = new ArrayList<>();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(false); //full
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
			ChunkWriterVariesWithLight.writeSectionsCompactFlattening(
				to, blockMask, 14,
				chunksections.blockDataRemappingTable,
				chunksections.flatteningBlockDataTable,
				chunksections.cachedChunk,
				chunksections.clientCache.hasDimensionSkyLight(),
				sectionNumber -> resendTiles.add(chunksections.cachedChunk.getTiles(sectionNumber).values())
			);
		});
		MiscSerializer.writeVarIntCountPrefixedType(chunkdata, resendTiles, (tilesTo, tiles) -> {
			int count = 0;
			for (Collection<TileEntity> sectionTiles : tiles) {
				for (TileEntity tile : sectionTiles) {
					ItemStackSerializer.writeDirectTag(tilesTo, tile.getNBT());
				}
				count += sectionTiles.size();
			}
			return count;
		});
		codec.write(chunkdata);
	}

}
