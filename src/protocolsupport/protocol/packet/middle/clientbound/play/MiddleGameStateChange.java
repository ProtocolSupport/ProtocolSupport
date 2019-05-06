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

	@Override
	public boolean postFromServerRead() {
		//TODO: restore rain after finding out what exactly causes rain particles to spawn in insane amounts and cause OOM
		return type != 2;
	}

}
