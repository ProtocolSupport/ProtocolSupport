package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleGameStateChange extends ClientBoundMiddlePacket {

	public MiddleGameStateChange(ConnectionImpl connection) {
		super(connection);
	}

	protected int type;
	protected float value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		type = serverdata.readByte();
		value = serverdata.readFloat();
	}

}
