package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleWorldEvent extends ClientBoundMiddlePacket {

	protected int effectId;
	protected Position position = new Position(0, 0, 0);
	protected int data;
	protected boolean disableRelative;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		effectId = serverdata.readInt();
		PositionSerializer.readPositionTo(serverdata, position);
		data = serverdata.readInt();
		disableRelative = serverdata.readBoolean();
	}

}
