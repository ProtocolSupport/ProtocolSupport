package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerBoat;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class SteerBoat extends MiddleSteerBoat {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		rightPaddleTurning = serializer.readBoolean();
		leftPaddleTurning = serializer.readBoolean();
	}

}
