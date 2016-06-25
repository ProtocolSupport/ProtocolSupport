package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r2;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chunk extends MiddleChunk<RecyclableCollection<PacketData>> {

	private final ChunkTransformer transformer = new ChunkTransformer();

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeBoolean(full);
		serializer.writeVarInt(bitmask);
		transformer.loadData(data, bitmask, storage.hasSkyLightInCurrentDimension(), full);
		serializer.writeByteArray(transformer.to19Data(version));
		serializer.writeVarInt(tiles.length);
		for (NBTTagCompoundWrapper tile : tiles) {
			serializer.writeTag(tile);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
