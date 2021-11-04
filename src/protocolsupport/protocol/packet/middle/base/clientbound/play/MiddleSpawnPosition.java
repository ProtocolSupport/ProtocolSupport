package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleSpawnPosition extends ClientBoundMiddlePacket {

	protected MiddleSpawnPosition(IMiddlePacketInit init) {
		super(init);
	}

	protected final Position position = new Position(0, 0, 0);
	protected float angle;

	@Override
	protected void decode(ByteBuf serverdata) {
		PositionCodec.readPosition(serverdata, position);
		angle = serverdata.readFloat();
	}

}
