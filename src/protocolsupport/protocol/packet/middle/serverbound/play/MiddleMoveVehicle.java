package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleMoveVehicle extends ServerBoundMiddlePacket {

	public MiddleMoveVehicle(ConnectionImpl connection) {
		super(connection);
	}

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;

	@Override
	public void writeToServer() {
		codec.read(create(x, y, z, yaw, pitch));
	}

	public static ServerBoundPacketData create(double x, double y, double z, float yaw, float pitch) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_MOVE_VEHICLE);
		creator.writeDouble(x);
		creator.writeDouble(y);
		creator.writeDouble(z);
		creator.writeFloat(yaw);
		creator.writeFloat(pitch);
		return creator;
	}

}
