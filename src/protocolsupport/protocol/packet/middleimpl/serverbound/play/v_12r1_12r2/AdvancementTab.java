package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAdvancementTab;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class AdvancementTab extends MiddleAdvancementTab {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		action = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		if (action == Action.OPEN) {
			identifier = StringSerializer.readString(clientdata, connection.getVersion());
		}
	}

}
