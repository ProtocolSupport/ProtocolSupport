package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleHeldSlot extends ClientBoundMiddlePacket {

	public MiddleHeldSlot(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		slot = serverdata.readUnsignedByte();
	}

}
