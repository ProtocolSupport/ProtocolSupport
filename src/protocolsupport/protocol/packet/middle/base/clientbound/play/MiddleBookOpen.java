package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleBookOpen extends ClientBoundMiddlePacket {

	protected MiddleBookOpen(IMiddlePacketInit init) {
		super(init);
	}

	protected UsedHand hand;

	@Override
	protected void decode(ByteBuf serverdata) {
		hand = MiscDataCodec.readVarIntEnum(serverdata, UsedHand.CONSTANT_LOOKUP);
	}

}
