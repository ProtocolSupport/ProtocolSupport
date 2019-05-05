package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.types.UsedHand;

public abstract class MiddleOpenBook extends ClientBoundMiddlePacket {

	public MiddleOpenBook(ConnectionImpl connection) {
		super(connection);
	}

	protected UsedHand hand;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		hand = MiscSerializer.readVarIntEnum(serverdata, UsedHand.CONSTANT_LOOKUP);
	}

}
