package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class MiddleEntityRelMove extends MiddleEntity {

	public MiddleEntityRelMove(ConnectionImpl connection) {
		super(connection);
	}

	protected short relX;
	protected short relY;
	protected short relZ;
	protected boolean onGround;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		onGround = serverdata.readBoolean();
	}

}
