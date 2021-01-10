package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleHeldSlot extends ServerBoundMiddlePacket {

	public MiddleHeldSlot(MiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void write() {
		codec.writeServerbound(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_HELD_SLOT);
		creator.writeShort(slot);
		return creator;
	}

}
