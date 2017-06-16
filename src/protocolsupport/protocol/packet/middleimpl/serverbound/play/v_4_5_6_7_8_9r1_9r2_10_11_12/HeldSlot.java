package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		slot = clientdata.readShort();
	}

}
