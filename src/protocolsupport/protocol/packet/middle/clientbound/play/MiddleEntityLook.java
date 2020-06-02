package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class MiddleEntityLook extends MiddleEntity {

	public MiddleEntityLook(ConnectionImpl connection) {
		super(connection);
	}

	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

}
