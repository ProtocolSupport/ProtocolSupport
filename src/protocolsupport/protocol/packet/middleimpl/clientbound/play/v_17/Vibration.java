package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVibration;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.VibrationPath;

public class Vibration extends MiddleVibration {

	protected Vibration(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData vibrationPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_VIBRATION);
		VibrationPath.writeNetworkData(vibrationPacket, path);
		codec.writeClientbound(vibrationPacket);
	}

}
