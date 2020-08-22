package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleHeldSlot extends ClientBoundMiddlePacket {

	public MiddleHeldSlot(MiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		slot = serverdata.readUnsignedByte();
	}

}
