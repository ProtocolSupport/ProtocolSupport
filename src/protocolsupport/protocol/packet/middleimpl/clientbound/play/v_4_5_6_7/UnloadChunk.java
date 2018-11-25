package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUnloadChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UnloadChunk extends MiddleUnloadChunk {

	public UnloadChunk(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public boolean postFromServerRead() {
		cache.getTileCache().removeChunk(chunk);
		return super.postFromServerRead();
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID);
		PositionSerializer.writeChunkCoord(serializer, chunk);
		serializer.writeBoolean(true);
		serializer.writeShort(0);
		serializer.writeShort(0);
		serializer.writeInt(0);
		return RecyclableSingletonList.create(serializer);
	}

}
