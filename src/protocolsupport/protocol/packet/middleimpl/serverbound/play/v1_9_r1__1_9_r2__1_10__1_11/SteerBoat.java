package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerBoat;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class SteerBoat extends MiddleSteerBoat {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		rightPaddleTurning = serializer.readBoolean();
		leftPaddleTurning = serializer.readBoolean();
	}

}
