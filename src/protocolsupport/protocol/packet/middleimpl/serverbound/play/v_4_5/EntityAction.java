package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEntityAction;

public class EntityAction extends MiddleEntityAction {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		entityId = clientdata.readInt();
		actionId = clientdata.readByte() - 1;
	}

}
