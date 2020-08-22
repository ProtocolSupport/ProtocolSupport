package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleBookOpen extends ClientBoundMiddlePacket {

	public MiddleBookOpen(MiddlePacketInit init) {
		super(init);
	}

	protected UsedHand hand;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		hand = MiscSerializer.readVarIntEnum(serverdata, UsedHand.CONSTANT_LOOKUP);
	}

}
