package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleInventoryHorseOpen extends ClientBoundMiddlePacket {

	public MiddleInventoryHorseOpen(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected int slots;
	protected int entityId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readByte();
		slots = VarNumberSerializer.readVarInt(serverdata);
		entityId = serverdata.readInt();
	}

}
