package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleUnloadChunk extends ClientBoundMiddlePacket {

	public MiddleUnloadChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected int chunkX;
	protected int chunkZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunkX = serverdata.readInt();
		chunkZ = serverdata.readInt();
	}

}
