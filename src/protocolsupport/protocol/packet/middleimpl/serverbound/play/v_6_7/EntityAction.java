package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		entityId = clientdata.readInt();
		actionId = clientdata.readByte() - 1;
		jumpBoost = clientdata.readInt();
		if (actionId == 6) {
			actionId = 7;
		}
	}

}
