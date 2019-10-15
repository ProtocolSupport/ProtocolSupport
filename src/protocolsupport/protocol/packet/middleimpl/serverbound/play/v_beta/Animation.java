package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.types.UsedHand;

public class Animation extends MiddleAnimation {

	public Animation(ConnectionImpl connection) {
		super(connection);
		hand = UsedHand.MAIN;
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readInt();
		clientdata.readByte();
	}

}
