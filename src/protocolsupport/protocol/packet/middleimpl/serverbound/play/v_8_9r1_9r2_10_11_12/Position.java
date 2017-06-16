package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePosition;

public class Position extends MiddlePosition {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		x = clientdata.readDouble();
		y = clientdata.readDouble();
		z = clientdata.readDouble();
		onGround = clientdata.readBoolean();
	}

}
