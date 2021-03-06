package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleMoveLook extends ServerBoundMiddlePacket {

	protected MiddleMoveLook(MiddlePacketInit init) {
		super(init);
	}

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	protected void write() {
		codec.writeServerbound(create(x, y, z, yaw, pitch, onGround));
	}

	public static ServerBoundPacketData create(double x, double y, double z, float yaw, float pitch, boolean onGround) {
		ServerBoundPacketData movelook = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_POSITION_LOOK);
		movelook.writeDouble(x);
		movelook.writeDouble(y);
		movelook.writeDouble(z);
		movelook.writeFloat(yaw);
		movelook.writeFloat(pitch);
		movelook.writeBoolean(onGround);
		return movelook;
	}
}
