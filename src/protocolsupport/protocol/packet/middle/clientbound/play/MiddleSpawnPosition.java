package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleSpawnPosition extends ClientBoundMiddlePacket {

	protected Position position;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		position = PositionSerializer.readPosition(serverdata);
	}

}
