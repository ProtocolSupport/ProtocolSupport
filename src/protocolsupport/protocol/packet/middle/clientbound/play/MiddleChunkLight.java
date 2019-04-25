package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleChunkLight extends ClientBoundMiddlePacket {

	//TODO: structure
	protected ByteBuf data;

	public MiddleChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		data = serverdata.readSlice(serverdata.readableBytes());
	}

}
