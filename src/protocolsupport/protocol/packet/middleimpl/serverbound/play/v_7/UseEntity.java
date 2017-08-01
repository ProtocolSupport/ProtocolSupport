package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = Action.values()[clientdata.readByte()];
	}

}
