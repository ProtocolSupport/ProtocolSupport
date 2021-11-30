package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2_18;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleVibration;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.types.VibrationPath;

public class Vibration extends MiddleVibration implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public Vibration(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData vibrationPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_VIBRATION);
		VibrationPath.writeNetworkData(vibrationPacket, path);
		io.writeClientbound(vibrationPacket);
	}

}
