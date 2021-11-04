package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleHeldSlot extends ClientBoundMiddlePacket {

	protected MiddleHeldSlot(IMiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void decode(ByteBuf serverdata) {
		slot = serverdata.readUnsignedByte();
	}

}
