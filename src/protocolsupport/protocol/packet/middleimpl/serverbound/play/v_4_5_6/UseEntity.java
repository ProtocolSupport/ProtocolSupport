package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readInt();
		entityId = clientdata.readInt();
		action = clientdata.readBoolean() ? Action.ATTACK : Action.INTERACT;
	}

}
