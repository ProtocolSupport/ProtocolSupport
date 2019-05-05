package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleSetViewCenter extends ClientBoundMiddlePacket {

	protected ChunkCoord chunk;

	public MiddleSetViewCenter(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunk = PositionSerializer.readVarIntChunkCoord(serverdata);
	}

}
