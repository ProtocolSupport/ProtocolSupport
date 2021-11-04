package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityVelocity extends MiddleEntityData {

	protected MiddleEntityVelocity(IMiddlePacketInit init) {
		super(init);
	}

	protected short velX;
	protected short velY;
	protected short velZ;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		velX = serverdata.readShort();
		velY = serverdata.readShort();
		velZ = serverdata.readShort();
	}

}
