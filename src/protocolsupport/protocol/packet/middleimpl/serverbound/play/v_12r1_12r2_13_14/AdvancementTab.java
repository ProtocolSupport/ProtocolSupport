package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAdvancementTab;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class AdvancementTab extends MiddleAdvancementTab {

	public AdvancementTab(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		action = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		if (action == Action.OPEN) {
			identifier = StringSerializer.readString(clientdata, version);
		}
	}

}
