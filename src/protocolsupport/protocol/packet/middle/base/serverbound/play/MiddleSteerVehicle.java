package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleSteerVehicle extends ServerBoundMiddlePacket {

	public static final int FLAGS_BIT_JUMPING = 0;
	public static final int FLAGS_BIT_UNMOUNT = 1;

	protected MiddleSteerVehicle(IMiddlePacketInit init) {
		super(init);
	}

	protected float sideForce;
	protected float forwardForce;
	protected int flags;

	@Override
	protected void write() {
		io.writeServerbound(create(sideForce, forwardForce, flags));
	}

	public static ServerBoundPacketData create(float sideForce, float forwardForce, int flags) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_STEER_VEHICLE);
		creator.writeFloat(sideForce);
		creator.writeFloat(forwardForce);
		creator.writeByte(flags);
		return creator;
	}

}
