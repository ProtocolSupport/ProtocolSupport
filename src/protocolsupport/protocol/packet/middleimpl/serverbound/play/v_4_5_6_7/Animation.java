package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;

public class Animation extends MiddleAnimation {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readInt();
		clientdata.readByte();
	}

}
