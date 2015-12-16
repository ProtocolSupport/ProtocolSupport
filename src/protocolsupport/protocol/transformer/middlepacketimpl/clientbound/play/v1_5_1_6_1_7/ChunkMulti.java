package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChunkMulti;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupport.protocol.transformer.utils.ChunkTransformer;
import protocolsupport.utils.CompressionUtils;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_6_2, ProtocolVersion.MINECRAFT_1_5_2})
public class ChunkMulti extends MiddleChunkMulti<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		ByteArrayOutputStream stream = new ByteArrayOutputStream(23000);
		for (int i = 0; i < data.length; i++) {
			stream.write(ChunkTransformer.toPre18Data(data[i], bitmap[i], version));
		}
		byte[] compressed = CompressionUtils.compress(stream.toByteArray());
		serializer.writeShort(data.length);
		serializer.writeInt(compressed.length);
		serializer.writeBoolean(hasSkyLight);
		serializer.writeBytes(compressed);
		for (int i = 0; i < data.length; i++) {
			serializer.writeInt(chunkX[i]);
			serializer.writeInt(chunkZ[i]);
			serializer.writeShort(bitmap[i]);
			serializer.writeShort(0);
		}
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_CHUNK_MULTI_ID, serializer));
	}

}
