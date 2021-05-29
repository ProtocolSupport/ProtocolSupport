package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSteerVehicle extends ServerBoundMiddlePacket {

	public static final int FLAGS_BIT_JUMPING = 0;
	public static final int FLAGS_BIT_UNMOUNT = 1;

	protected MiddleSteerVehicle(MiddlePacketInit init) {
		super(init);
	}

	protected float sideForce;
	protected float forwardForce;
	protected int flags;

	@Override
	protected void write() {
		codec.writeServerbound(create(sideForce, forwardForce, flags));
	}

	public static ServerBoundPacketData create(float sideForce, float forwardForce, int flags) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_STEER_VEHICLE);
		creator.writeFloat(sideForce);
		creator.writeFloat(forwardForce);
		creator.writeByte(flags);
		return creator;
	}

}
