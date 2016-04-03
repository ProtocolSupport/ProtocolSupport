package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChunk;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.chunk.ChunkTransformer;
import protocolsupport.protocol.transformer.utils.chunk.ChunkUtils;
import protocolsupport.utils.netty.Compressor;
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
		serializer.writeShort(bitmask);
		serializer.writeShort(0);
		transformer.loadData(data, bitmask, ChunkUtils.hasSkyLight(player.getWorld()), full);
		byte[] compressed = Compressor.compressStatic(transformer.toPre18Data(version));
		serializer.writeInt(compressed.length);
		serializer.writeBytes(compressed);
		return RecyclableSingletonList.create(serializer);
	}

}
