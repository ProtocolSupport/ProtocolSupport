package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityHurtAnimation extends MiddleEntityData {

	protected MiddleEntityHurtAnimation(IMiddlePacketInit init) {
		super(init);
	}

	protected float yaw;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		this.yaw = serverdata.readFloat();
	}

}
