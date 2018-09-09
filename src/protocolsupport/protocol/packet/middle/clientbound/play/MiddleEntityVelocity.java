package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class MiddleEntityVelocity extends MiddleEntity {

	public MiddleEntityVelocity(ConnectionImpl connection) {
		super(connection);
	}

	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
	}

}
