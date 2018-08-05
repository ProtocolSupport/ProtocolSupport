package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleTimeUpdate extends ClientBoundMiddlePacket {

	public MiddleTimeUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected long worldAge;
	protected long timeOfDay;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		worldAge = serverdata.readLong();
		timeOfDay = serverdata.readLong();
	}

}
