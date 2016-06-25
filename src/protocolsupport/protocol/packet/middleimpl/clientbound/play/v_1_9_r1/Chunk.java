package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1.BlockTileUpdate;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Chunk extends MiddleChunk<RecyclableCollection<PacketData>> {

	private final ChunkTransformer transformer = new ChunkTransformer();

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		RecyclableArrayList<PacketData> packets = RecyclableArrayList.create();
		PacketData chunkdata = PacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		chunkdata.writeInt(chunkX);
		chunkdata.writeInt(chunkZ);
		chunkdata.writeBoolean(full);
		chunkdata.writeVarInt(bitmask);
		transformer.loadData(data, bitmask, storage.hasSkyLightInCurrentDimension(), full);
		chunkdata.writeByteArray(transformer.to19Data(version));
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
