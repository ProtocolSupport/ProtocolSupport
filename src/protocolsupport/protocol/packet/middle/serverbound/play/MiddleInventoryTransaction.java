package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryTransaction extends ServerBoundMiddlePacket {

	public MiddleInventoryTransaction(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected int actionNumber;
	protected boolean accepted;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		return RecyclableSingletonList.create(create(windowId, actionNumber, accepted));
	}

	public static ServerBoundPacketData create(int windowId, int actionNumber, boolean accepted) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_WINDOW_TRANSACTION);
		creator.writeByte(windowId);
		creator.writeShort(actionNumber);
		creator.writeBoolean(accepted);
		return creator;
	}

}
