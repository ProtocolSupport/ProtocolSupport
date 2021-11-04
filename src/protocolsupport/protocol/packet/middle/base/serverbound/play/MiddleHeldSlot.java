package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleHeldSlot extends ServerBoundMiddlePacket {

	protected MiddleHeldSlot(IMiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void write() {
		io.writeServerbound(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_HELD_SLOT);
		creator.writeShort(slot);
		return creator;
	}

}
