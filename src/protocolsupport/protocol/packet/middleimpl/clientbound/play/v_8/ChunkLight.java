package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import java.util.ArrayList;
import java.util.List;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkLight;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterShort;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class ChunkLight extends AbstractChunkLight {

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public ChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	protected final List<ClientBoundPacketData> blocktileupdates = new ArrayList<>();

	@Override
	public void writeToClient() {
		int blockMask = ((setSkyLightMask | setBlockLightMask | emptySkyLightMask | emptyBlockLightMask) >> 1) & 0xFFFF;
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(false); //full
		chunkdata.writeShort(blockMask);
		ArraySerializer.writeVarIntByteArray(chunkdata, ChunkWriterShort.serializeSections(
			blockMask, blockDataRemappingTable, cachedChunk, hasSkyLight,
			sectionNumber -> cachedChunk.getTiles(sectionNumber).values().forEach(tile -> blocktileupdates.add(BlockTileUpdate.create(version, tile)))
		));

		codec.write(chunkdata);
		for (ClientBoundPacketData blocktileupdate : blocktileupdates) {
			codec.write(blocktileupdate);
		}
	}

	@Override
	public void cleanup() {
		blocktileupdates.clear();
	}

}
