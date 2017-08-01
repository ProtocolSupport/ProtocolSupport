package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAdvancementTab;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class AdvancementTab extends MiddleAdvancementTab {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		action = MiscSerializer.readEnum(clientdata, Action.class);
		if (action == Action.OPEN) {
			identifier = StringSerializer.readString(clientdata, connection.getVersion());
		}
	}

}
