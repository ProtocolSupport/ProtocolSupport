package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.EmptyChunk;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1.BlockTileUpdate;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk<RecyclableCollection<PacketData>>  {

	private final ChunkTransformer transformer = new ChunkTransformer();

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		RecyclableArrayList<PacketData> packets = RecyclableArrayList.create();
		PacketData chunkdata = PacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		chunkdata.writeInt(chunkX);
		chunkdata.writeInt(chunkZ);
		chunkdata.writeBoolean(full);
		boolean hasSkyLight = storage.hasSkyLightInCurrentDimension();
		if (bitmask == 0 && full) {
			chunkdata.writeShort(1);
			chunkdata.writeByteArray(EmptyChunk.get18ChunkData(hasSkyLight));
		} else {
			chunkdata.writeShort(bitmask);
			transformer.loadData(data, bitmask, hasSkyLight, full);
			chunkdata.writeByteArray(transformer.to18Data());
		}
		packets.add(chunkdata);
		for (NBTTagCompoundWrapper tile : tiles) {
			packets.add(BlockTileUpdate.createPacketData(
				version,
				LegacyTileEntityUpdate.getPosition(tile),
				LegacyTileEntityUpdate.getUpdateType(tile).ordinal(),
				tile
			));
		}
		return packets;
	}

}
