package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryTransaction extends ServerBoundMiddlePacket {

	protected int windowId;
	protected int actionNumber;
	protected boolean accepted;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_WINDOW_TRANSACTION.get());
		creator.writeByte(windowId);
		creator.writeShort(actionNumber);
		creator.writeBoolean(accepted);
		return RecyclableSingletonList.create(creator.create());
	}

}
