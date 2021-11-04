package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleMoveVehicle extends ServerBoundMiddlePacket {

	protected MiddleMoveVehicle(IMiddlePacketInit init) {
		super(init);
	}

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;

	@Override
	protected void write() {
		io.writeServerbound(create(x, y, z, yaw, pitch));
	}

	public static ServerBoundPacketData create(double x, double y, double z, float yaw, float pitch) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_MOVE_VEHICLE);
		creator.writeDouble(x);
		creator.writeDouble(y);
		creator.writeDouble(z);
		creator.writeFloat(yaw);
		creator.writeFloat(pitch);
		return creator;
	}

}
