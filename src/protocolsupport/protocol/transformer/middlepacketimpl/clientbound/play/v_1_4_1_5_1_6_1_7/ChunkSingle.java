package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChunkSingle;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.ChunkTransformer;
import protocolsupport.utils.CompressionUtils;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class ChunkSingle extends MiddleChunkSingle<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeBoolean(cont);
		serializer.writeShort(bitmask);
		serializer.writeShort(0);
		byte[] compressed = CompressionUtils.compress(ChunkTransformer.toPre18Data(data, bitmask, version));
		serializer.writeInt(compressed.length);
		serializer.writeBytes(compressed);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, serializer));
	}

}
