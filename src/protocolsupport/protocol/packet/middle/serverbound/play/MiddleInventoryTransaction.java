package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryTransaction extends ServerBoundMiddlePacket {

	protected int windowId;
	protected int actionNumber;
	protected boolean accepted;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_WINDOW_TRANSACTION);
		creator.writeByte(windowId);
		creator.writeShort(actionNumber);
		creator.writeBoolean(accepted);
		return RecyclableSingletonList.create(creator);
	}

}
