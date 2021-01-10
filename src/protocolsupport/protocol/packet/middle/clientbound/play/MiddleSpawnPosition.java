package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;

public abstract class MiddleSpawnPosition extends ClientBoundMiddlePacket {

	public MiddleSpawnPosition(MiddlePacketInit init) {
		super(init);
	}

	protected final Position position = new Position(0, 0, 0);

	@Override
	protected void decode(ByteBuf serverdata) {
		PositionSerializer.readPositionTo(serverdata, position);
	}

}
