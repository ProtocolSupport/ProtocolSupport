package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2_13_14r1_14r2_15_16;

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
	protected void readClientData(ByteBuf clientdata) {
		action = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		if (action == Action.OPEN) {
			identifier = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		}
	}

}
