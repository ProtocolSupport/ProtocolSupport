package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleHeldSlot extends ServerBoundMiddlePacket {

	public MiddleHeldSlot(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;

	@Override
	public void writeToServer() {
		codec.read(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_HELD_SLOT);
		creator.writeShort(slot);
		return creator;
	}

}
