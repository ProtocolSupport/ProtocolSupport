package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkUnload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class ChunkUnload extends MiddleChunkUnload {

	public ChunkUnload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData chunkunload = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD);
		PositionSerializer.writeIntChunkCoord(chunkunload, chunk);
		codec.write(chunkunload);
	}

}
