package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleMoveLook extends ServerBoundMiddlePacket {

	protected MiddleMoveLook(IMiddlePacketInit init) {
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
		io.writeServerbound(create(x, y, z, yaw, pitch, onGround));
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
