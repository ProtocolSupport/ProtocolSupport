package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class MiddleEntityHeadRotation extends MiddleEntity {

	public MiddleEntityHeadRotation(ConnectionImpl connection) {
		super(connection);
	}

	protected byte headRot;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		headRot = serverdata.readByte();
	}

}
