package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.types.UsedHand;

public class UseItem extends MiddleBlockPlace {

	public UseItem(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		hand = MiscSerializer.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
		face = -1;
	}

}
