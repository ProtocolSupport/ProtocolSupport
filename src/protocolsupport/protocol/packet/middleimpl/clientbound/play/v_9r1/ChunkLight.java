package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkLight;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class ChunkLight extends AbstractChunkLight {

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public ChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (preChunk) {
			return RecyclableEmptyList.get();
		} else {
			int blockMask = ((setSkyLightMask | setBlockLightMask | emptySkyLightMask | emptyBlockLightMask) >> 1) & 0xFFFF;
			boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();

			ClientBoundPacketData chunkdata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
			PositionSerializer.writeIntChunkCoord(chunkdata, coord);
			chunkdata.writeBoolean(false); //full
			VarNumberSerializer.writeVarInt(chunkdata, blockMask);
			ArraySerializer.writeVarIntByteArray(chunkdata, to -> {
				ChunkWriterVariesWithLight.writeSectionsPreFlattening(
					to, blockMask, 13,
					blockDataRemappingTable,
					cachedChunk, hasSkyLight,
					sectionNumber -> cachedChunk.getTiles(sectionNumber).values().forEach(tile -> packets.add(BlockTileUpdate.create(version, tile)))
				);
			});
			packets.add(0, chunkdata);

			return packets;
		}
	}

}
