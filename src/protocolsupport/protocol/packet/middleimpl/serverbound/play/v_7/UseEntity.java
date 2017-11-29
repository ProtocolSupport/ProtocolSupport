package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUseEntity;
import protocolsupport.protocol.serializer.MiscSerializer;

public class UseEntity extends MiddleUseEntity {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = clientdata.readInt();
		action = MiscSerializer.readByteEnum(clientdata, Action.CONSTANT_LOOKUP);
	}

}
