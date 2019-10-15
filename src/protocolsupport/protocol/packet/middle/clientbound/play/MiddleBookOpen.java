package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleBookOpen extends ClientBoundMiddlePacket {

	public MiddleBookOpen(ConnectionImpl connection) {
		super(connection);
	}

	protected UsedHand hand;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		hand = MiscSerializer.readVarIntEnum(serverdata, UsedHand.CONSTANT_LOOKUP);
	}

}
