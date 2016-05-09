package protocolsupport.protocol.packet.middlepacket.serverbound.play;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePosition extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected boolean onGround;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		if (!sharedstorage.isTeleportConfirmNeeded()) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_POSITION.get());
			creator.writeDouble(x);
			creator.writeDouble(y);
			creator.writeDouble(z);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.create(creator.create());
		} else {
			return RecyclableEmptyList.get();
		}
	}

}
