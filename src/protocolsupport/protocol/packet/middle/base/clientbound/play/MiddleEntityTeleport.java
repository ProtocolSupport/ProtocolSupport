package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityTeleport extends MiddleEntityData {

	protected MiddleEntityTeleport(IMiddlePacketInit init) {
		super(init);
	}

	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

	@Override
	protected void decodeDataLast(ByteBuf serverdata) {
		entity.getDataCache().setLocation(x, y, z, pitch, yaw);
	}

}
