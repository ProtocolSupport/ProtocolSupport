package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.EmptyChunk;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chunk extends MiddleChunk<RecyclableCollection<PacketData>>  {

	private final ChunkTransformer transformer = new ChunkTransformer();

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeBoolean(full);
		boolean hasSkyLight = storage.hasSkyLightInCurrentDimension();
		if (bitmask == 0 && full) {
			serializer.writeShort(1);
			serializer.writeArray(EmptyChunk.get18ChunkData(hasSkyLight));
		} else {
			serializer.writeShort(bitmask);
			transformer.loadData(data, bitmask, hasSkyLight, full);
			serializer.writeArray(transformer.to18Data());
		}
		return RecyclableSingletonList.create(serializer);
	}

}
