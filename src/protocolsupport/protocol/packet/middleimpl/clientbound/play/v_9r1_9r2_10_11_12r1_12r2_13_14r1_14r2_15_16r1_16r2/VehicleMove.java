package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVehicleMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class VehicleMove extends MiddleVehicleMove {

	public VehicleMove(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData vehiclemove = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_VEHICLE_MOVE);
		vehiclemove.writeDouble(x);
		vehiclemove.writeDouble(y);
		vehiclemove.writeDouble(z);
		vehiclemove.writeFloat(yaw);
		vehiclemove.writeFloat(pitch);
		codec.writeClientbound(vehiclemove);
	}

}
