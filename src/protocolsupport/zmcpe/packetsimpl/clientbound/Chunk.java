package protocolsupport.zmcpe.packetsimpl.clientbound;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class Chunk extends MiddleChunk<RecyclableCollection<ClientBoundPacketData>> {

	private final ChunkTransformer transformer = ChunkTransformer.create(BlockFormat.PE);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA, version);
		serializer.writeSVarInt(chunkX);
		serializer.writeSVarInt(chunkZ);
		transformer.loadData(data, bitmask, cache.hasSkyLightInCurrentDimension(), full);
		ProtocolSupportPacketDataSerializer chunkdata = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), version);
		chunkdata.writeByte(Integer.bitCount(bitmask));
		chunkdata.writeBytes(transformer.toLegacyData(version));
		chunkdata.writeBytes(new byte[512]); //heightmap (actually shorts), TODO: calculate it
		chunkdata.writeBytes(new byte[256]); //biomes TODO: write them
		chunkdata.writeByte(0); //borders???
		chunkdata.writeSVarInt(0); //extra data???
		chunkdata.writeBytes(new byte[0]); //nbt tiles TODO
		serializer.writeByteArray(chunkdata);
		return RecyclableSingletonList.create(serializer);
	}

}
