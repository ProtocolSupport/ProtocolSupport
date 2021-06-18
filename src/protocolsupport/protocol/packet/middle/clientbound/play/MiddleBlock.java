package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleBlock extends ClientBoundMiddlePacket {

	protected MiddleBlock(MiddlePacketInit init) {
		super(init);
	}

	protected final Position position = new Position(0, 0, 0);

	@Override
	protected void decode(ByteBuf serverdata) {
		PositionCodec.readPosition(serverdata, position);
	}

}
