package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class MiddleEntityLook extends MiddleEntity {

	public MiddleEntityLook(ConnectionImpl connection) {
		super(connection);
	}

	protected int yaw;
	protected int pitch;
	protected boolean onGround;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

}
