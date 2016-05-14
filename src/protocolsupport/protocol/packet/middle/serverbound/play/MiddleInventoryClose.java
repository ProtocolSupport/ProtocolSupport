package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryClose extends ServerBoundMiddlePacket {

	protected int windowId;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		sharedstorage.closeWindow();
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_WINDOW_CLOSE);
		creator.writeByte(windowId);
		return RecyclableSingletonList.create(creator);
	}

}
