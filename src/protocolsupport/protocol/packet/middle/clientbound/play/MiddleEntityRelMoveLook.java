package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class MiddleEntityRelMoveLook extends MiddleEntity {

	public MiddleEntityRelMoveLook(ConnectionImpl connection) {
		super(connection);
	}

	protected int relX;
	protected int relY;
	protected int relZ;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	public void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

}
