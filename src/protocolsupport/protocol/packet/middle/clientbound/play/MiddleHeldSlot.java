package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleHeldSlot extends ClientBoundMiddlePacket {

	protected int slot;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		slot = serverdata.readUnsignedByte();
	}

}
