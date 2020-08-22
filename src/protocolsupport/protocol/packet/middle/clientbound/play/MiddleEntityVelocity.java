package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityVelocity extends MiddleEntity {

	public MiddleEntityVelocity(MiddlePacketInit init) {
		super(init);
	}

	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();
	}

}
