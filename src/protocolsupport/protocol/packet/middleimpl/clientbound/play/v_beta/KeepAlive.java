package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;

public class KeepAlive extends ClientBoundMiddlePacket {

	public KeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	protected long keepAliveId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		keepAliveId = serverdata.readLong();
	}

	@Override
	public void writeToClient() {
		codec.readAndComplete(MiddleKeepAlive.create(keepAliveId));
	}

}
