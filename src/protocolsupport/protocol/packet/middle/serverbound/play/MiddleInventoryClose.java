package protocolsupport.protocol.packet.middle.serverbound.play;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryClose extends ServerBoundMiddlePacket {

	protected int windowId;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		sharedstorage.closeWindow();
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_WINDOW_CLOSE.get());
		creator.writeByte(windowId);
		return RecyclableSingletonList.create(creator.create());
	}

}
