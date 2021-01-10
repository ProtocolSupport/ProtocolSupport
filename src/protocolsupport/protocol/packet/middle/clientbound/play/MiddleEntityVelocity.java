package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityVelocity extends MiddleEntity {

	public MiddleEntityVelocity(MiddlePacketInit init) {
		super(init);
	}

	protected short velX;
	protected short velY;
	protected short velZ;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		velX = serverdata.readShort();
		velY = serverdata.readShort();
		velZ = serverdata.readShort();
	}

}
