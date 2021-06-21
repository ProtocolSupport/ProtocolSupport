package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityLook extends MiddleEntityData {

	protected MiddleEntityLook(MiddlePacketInit init) {
		super(init);
	}

	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

	@Override
	protected void handle() {
		entity.getDataCache().setLook(pitch, yaw);
	}

}
