package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleInventoryTransaction extends ServerBoundMiddlePacket {

	protected MiddleInventoryTransaction(MiddlePacketInit init) {
		super(init);
	}

	protected int windowId;
	protected int actionNumber;
	protected boolean accepted;

	@Override
	protected void write() {
		codec.writeServerbound(create(windowId, actionNumber, accepted));
	}

	public static ServerBoundPacketData create(int windowId, int actionNumber, boolean accepted) {
		ServerBoundPacketData inventorytransaction = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_WINDOW_TRANSACTION);
		inventorytransaction.writeByte(windowId);
		inventorytransaction.writeShort(actionNumber);
		inventorytransaction.writeBoolean(accepted);
		return inventorytransaction;
	}

}
