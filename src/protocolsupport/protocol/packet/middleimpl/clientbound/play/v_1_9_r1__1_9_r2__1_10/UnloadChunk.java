package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2__1_10;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnloadChunk;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UnloadChunk extends MiddleUnloadChunk<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CHUNK_UNLOAD, version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		return RecyclableSingletonList.create(serializer);
	}

}
