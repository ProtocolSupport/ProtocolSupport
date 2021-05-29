package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleHeldSlot extends ClientBoundMiddlePacket {

	protected MiddleHeldSlot(MiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void decode(ByteBuf serverdata) {
		slot = serverdata.readUnsignedByte();
	}

}
