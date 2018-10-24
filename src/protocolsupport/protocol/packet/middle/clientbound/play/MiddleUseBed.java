package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleUseBed extends ClientBoundMiddlePacket {

	public MiddleUseBed(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected final Position bed = new Position(0, 0, 0);

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		PositionSerializer.readPositionTo(serverdata, bed);
	}

}
