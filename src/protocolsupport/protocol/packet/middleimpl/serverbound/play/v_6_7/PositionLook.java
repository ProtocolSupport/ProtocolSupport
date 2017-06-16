package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;

public class PositionLook extends MiddlePositionLook {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		 x = clientdata.readDouble();
		 y = clientdata.readDouble();
		 clientdata.readDouble();
		 z = clientdata.readDouble();
		 yaw = clientdata.readFloat();
		 pitch = clientdata.readFloat();
		 onGround = clientdata.readBoolean();
	}

}
