package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer.BlockFormat;
import protocolsupport.protocol.packet.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ByteArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chunk extends MiddleChunk {

	private final ChunkTransformer transformer = ChunkTransformer.create(BlockFormat.PE);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA, version);
		VarNumberSerializer.writeSVarInt(serializer, chunkX);
		VarNumberSerializer.writeSVarInt(serializer, chunkZ);
		transformer.loadData(data, bitmask, cache.hasSkyLightInCurrentDimension(), full);
		ByteBuf chunkdata = Unpooled.buffer();
		chunkdata.writeByte(Integer.bitCount(bitmask));
		chunkdata.writeBytes(transformer.toLegacyData(version));
		chunkdata.writeBytes(new byte[512]); //heightmap (actually shorts), TODO: calculate it
		chunkdata.writeBytes(new byte[256]); //biomes TODO: write them
		chunkdata.writeByte(0); //borders???
		VarNumberSerializer.writeSVarInt(chunkdata, 0); //extra data???
		chunkdata.writeBytes(new byte[0]); //nbt tiles TODO
		ByteArraySerializer.writeByteArray(serializer, version, chunkdata);
		return RecyclableSingletonList.create(serializer);
	}

}
